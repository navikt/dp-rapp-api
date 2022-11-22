package no.nav.raptus.dprapp.db.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Slf4j
@Repository
public class KallLoggPartisjonHandterer {
    @Autowired
    DataSource dataSource;

    public void handterPartisjoner() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        try {
            jdbcTemplate.execute("SELECT handter_partisjoner_kall_logg()");
        } catch (DataAccessException e) {
            log.warn("Feil ved DB-kall til handter_partisjoner_kall_logg: " + e.getMessage(), e);
        }
    }
}
