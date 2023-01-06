package no.nav.raptus.dprapp.db.repository;

import lombok.extern.slf4j.Slf4j;
import no.nav.raptus.dprapp.db.entity.Meldeperiode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class MeldeperioderDAO {

    @Autowired
    DataSource dataSource;

    public List<Meldeperiode> hentListe() {
        List<Meldeperiode> meldeperioder = new ArrayList<>();
        meldeperioder.add(
                Meldeperiode.builder()
                        .meldeperiodeId(1L)
                        .fomDato(LocalDate.of(2022, 11, 27))
                        .tomDato(LocalDate.of(2022, 12, 4))
                        .frist(LocalDate.of(2022, 12, 5))
                        .build()
        );
        meldeperioder.add(
                Meldeperiode.builder()
                        .meldeperiodeId(2L)
                        .fomDato(LocalDate.of(2022, 12, 5))
                        .tomDato(LocalDate.of(2022, 12, 18))
                        .frist(LocalDate.of(2022, 12, 19))
                        .build()
        );
        meldeperioder.add(
                Meldeperiode.builder()
                        .meldeperiodeId(3L)
                        .fomDato(LocalDate.of(2022, 12, 19))
                        .tomDato(LocalDate.of(2023, 1, 1))
                        .frist(LocalDate.of(2023, 1, 2))
                        .build()
        );

        return meldeperioder;
    }

    public Meldeperiode hent(long meldeperiodeId) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        String selectStatement = "SELECT * FROM meldeperiode WHERE meldeperiode_id = :meldeperiodeId";

        List<Meldeperiode> result = namedParameterJdbcTemplate.query(
                selectStatement,
                new MapSqlParameterSource().addValue("meldeperiodeId", meldeperiodeId),
                new MeldeperiodeRowMapper()
        );

        if (result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }

    public String hentMellomlagret(long meldeperiodeId) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        String selectStatement = "SELECT data FROM mellomlgagret_meldeperiode WHERE meldeperiode_id = :meldeperiodeId";

        List<String> result = namedParameterJdbcTemplate.query(
                selectStatement,
                new MapSqlParameterSource().addValue("meldeperiodeId", meldeperiodeId),
                new DataRowMapper()
        );

        if (result.isEmpty()) {
            return "{}";
        }

        return result.get(0);
    }

    public void lagre(long meldeperiodeId, String data) {

        try {
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

            String insertStatement = "INSERT INTO mellomlgagret_meldeperiode " +
                    "( meldeperiode_id , data) " +
                    "VALUES " +
                    "( :meldeperiodeId , :data) " +
                    "ON CONFLICT (meldeperiode_id) DO " +
                    "UPDATE SET data = :data";

            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("meldeperiodeId", meldeperiodeId, Types.INTEGER);
            mapSqlParameterSource.addValue("data", data, Types.VARCHAR);

            namedParameterJdbcTemplate.update(insertStatement, mapSqlParameterSource);

        } catch (Exception e) {
            log.warn("Feil ved lagring av meldeperiode: " + e.getMessage(), e);
        }
    }

    public void slett(long meldeperiodeId) {

        try {
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

            String insertStatement = "DELETE FROM mellomlgagret_meldeperiode WHERE meldeperiode_id = :meldeperiodeId";

            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("meldeperiodeId", meldeperiodeId, Types.INTEGER);

            namedParameterJdbcTemplate.update(insertStatement, mapSqlParameterSource);

        } catch (Exception e) {
            log.warn("Feil ved sletting av meldeperiode: " + e.getMessage(), e);
        }
    }

    static class MeldeperiodeRowMapper implements RowMapper<Meldeperiode> {
        @Override
        public Meldeperiode mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Meldeperiode(
                    rs.getLong("meldeperiode_id"),
                    rs.getDate("fom_dato").toLocalDate(),
                    rs.getDate("tom_dato").toLocalDate(),
                    rs.getDate("frist").toLocalDate()
            );
        }
    }

    static class DataRowMapper implements RowMapper<String> {
        @Override
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getString("data");
        }
    }
}
