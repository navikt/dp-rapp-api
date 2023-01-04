package no.nav.raptus.dprapp.db.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Slf4j
@Repository
public class MellomlagretMeldeperiodeDAO {

    @Autowired
    DataSource dataSource;

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

    public String hente(long meldeperiodeId) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        String selectStatement = "SELECT data FROM mellomlgagret_meldeperiode WHERE meldeperiode_id = :meldeperiodeId";

        List<String> result = namedParameterJdbcTemplate.query(
                selectStatement,
                new MapSqlParameterSource().addValue("meldeperiodeId", meldeperiodeId),
                new DataRowMapper()
        );

        if (result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }

    static class DataRowMapper implements RowMapper<String> {
        @Override
        public java.lang.String mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getString("data");
        }
    }
}
