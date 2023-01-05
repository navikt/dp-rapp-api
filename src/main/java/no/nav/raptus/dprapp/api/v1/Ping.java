package no.nav.raptus.dprapp.api.v1;

import lombok.extern.slf4j.Slf4j;
import no.nav.security.token.support.core.api.Protected;
import no.nav.security.token.support.core.api.Unprotected;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static no.nav.raptus.dprapp.Konstanter.API_PATH;

@Slf4j
@Unprotected
@RestController
@RequestMapping(API_PATH)
public class Ping {

    @GetMapping(path = "/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok().build();
    }

    @Protected
    @GetMapping(path = "/authenticatedping")
    public ResponseEntity<String> authenticatedping() {
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
