package no.nav.raptus.dprapp.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ping")
public class Ping {

    private static Logger logger = LoggerFactory.getLogger(Ping.class);

    @GetMapping
    public ResponseEntity<Void> ping() {
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/error")
    public ResponseEntity<String> error() {
        logger.error("Test error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Test Error");
    }
}
