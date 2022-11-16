package no.nav.raptus.dprapp.db.repository;

import lombok.extern.slf4j.Slf4j;
import no.nav.raptus.dprapp.db.entity.KallLogg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;

@Slf4j
@Repository
public class KallLoggDAO {

    @Autowired
    DataSource dataSource;

    public String create(KallLogg kallLogg) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

            String insertStatement = "INSERT INTO kall_logg " +
                    "( korrelasjon_id" +
                    //", tidspunkt" +
                    ", type" +
                    ", retning" +
                    ", metode" +
                    ", path" +
                    ", status" +
                    ", kalltid" +
                    ", request" +
                    ", response" +
                    ", info ) " +
                    "VALUES " +
                    "( :korrelasjonId" +
                    //", :tidspunkt" +
                    ", :type" +
                    ", :retning" +
                    ", :metode" +
                    ", :path" +
                    ", :status" +
                    ", :kalltid" +
                    ", :request" +
                    ", :response" +
                    ", :info ) ";

            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

            mapSqlParameterSource.addValue("korrelasjonId", kallLogg.getKorrelasjonId(), Types.VARCHAR);
            //mapSqlParameterSource.addValue("tidspunkt", kallLogg.getTidspunkt(), Types.TIMESTAMP);
            mapSqlParameterSource.addValue("type", kallLogg.getType(), Types.VARCHAR);
            mapSqlParameterSource.addValue("retning", kallLogg.getRetning(), Types.VARCHAR);
            mapSqlParameterSource.addValue("metode", kallLogg.getMetode(), Types.VARCHAR);
            mapSqlParameterSource.addValue("path", kallLogg.getPath(), Types.VARCHAR);
            mapSqlParameterSource.addValue("status", kallLogg.getStatus(), Types.INTEGER);
            mapSqlParameterSource.addValue("kalltid", kallLogg.getKalltid(), Types.BIGINT);
            mapSqlParameterSource.addValue("request", kallLogg.getRequest(), Types.VARCHAR);
            mapSqlParameterSource.addValue("response", kallLogg.getResponse(), Types.VARCHAR);
            mapSqlParameterSource.addValue("info", kallLogg.getInfo(), Types.VARCHAR);
            namedParameterJdbcTemplate.update(insertStatement, mapSqlParameterSource, keyHolder);

        } catch (Exception e) {
            log.warn("Feil ved opprettelse av kall_logg: " + e.getMessage(), e);
        }
        return keyHolder.getKey().toString();
    }
}
