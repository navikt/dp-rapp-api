package no.nav.raptus.dprapp.db.repository;

import lombok.extern.slf4j.Slf4j;
import no.nav.raptus.dprapp.db.entity.Meldeperiode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Slf4j
@Repository
@Transactional
public class MeldeperioderDAO {

    @Autowired
    DataSource dataSource;

    public List<Meldeperiode> hentListe(String fnr) {

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        String selectStatement = "SELECT MP.* " +
                "FROM MELDEPERIODE MP " +
                "JOIN RAPPORTERING R ON MP.MELDEPERIODE_ID = R.MELDEPERIODE_ID AND R.STATUS = 'NY' " +
                "JOIN PERSON P ON P.PERSON_ID = R.PERSON_ID " +
                "WHERE P.FNR = :fnr " +
                "ORDER BY MP.frist";

        return namedParameterJdbcTemplate.query(
                selectStatement,
                new MapSqlParameterSource().addValue("fnr", fnr),
                new MeldeperiodeRowMapper()
        );
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

            log.warn("INSERT INTO mellomlgagret_meldeperiode " +
                    "( meldeperiode_id , data) " +
                    "VALUES " +
                    "( " + meldeperiodeId + " , " + data + ") " +
                    "ON CONFLICT (meldeperiode_id) DO " +
                    "UPDATE SET data = :data");

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
