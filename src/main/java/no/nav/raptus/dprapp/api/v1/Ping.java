package no.nav.raptus.dprapp.api.v1;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class Ping {

    @GetMapping(path = "/ping")
    public ResponseEntity<String> ping() {
        try {
            Thread.sleep(2500);
        } catch (Exception e) {
            log.warn("Feil med sleep: " + e.getMessage(), e);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/ping/error")
    public ResponseEntity<String> error() {
        log.error("Test error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Test Error");
    }

    @GetMapping(path = "/ping/warn")
    public ResponseEntity<Void> warn() {
        log.warn("Test warning");
        return ResponseEntity.ok().build();
    }
}
