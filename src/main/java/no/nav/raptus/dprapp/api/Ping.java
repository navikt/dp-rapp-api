package no.nav.raptus.dprapp.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.SQLException;

@RestController
@RequestMapping("ping")
public class Ping {

    @Autowired
    public DataSource dataSource;

    private static Logger logger = LoggerFactory.getLogger(Ping.class);

    @GetMapping
    public ResponseEntity<String> ping() {
        try {
            dataSource.getConnection().commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/error")
    public ResponseEntity<String> error() {
        logger.error("Test error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Test Error");
    }

    @GetMapping(path = "/warn")
    public ResponseEntity<Void> warn() {
        logger.warn("Test warning");
        return ResponseEntity.ok().build();
    }
}
