package no.nav.raptus.dprapp.api.v1;

import lombok.extern.slf4j.Slf4j;
import no.nav.raptus.dprapp.db.repository.MellomlagretMeldeperiodeDAO;
import no.nav.raptus.dprapp.model.Data;
import no.nav.security.token.support.core.api.Protected;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static no.nav.raptus.dprapp.Konstanter.API_PATH;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Protected
@RestController
@RequestMapping(API_PATH + "/mellomlagring")
public class Mellomlagring {

    @Autowired
    MellomlagretMeldeperiodeDAO mellomlagretMeldeperiodeDAO;

    @PostMapping(path = "/lagre")
    public ResponseEntity<String> lagre(
            @RequestHeader(value = AUTHORIZATION, required = false) String authorization,
            @RequestBody Data data
    ) {
        // Sjekk at bruker i token "eier" innsendte meldeperiode
        log.warn(authorization);

        // Lagre data
        try {
            mellomlagretMeldeperiodeDAO.lagre(data.getId(), data.toString()); // overriden data.toString() returns serialized object
        } catch (Exception e) {
            log.warn("Kunne ikke mellomlagre meldeperiode", e);
        }

        // Svar
        return ResponseEntity.ok().build();
    }

    @GetMapping("/hente/{id}")
    public ResponseEntity<String> hente(
            @RequestHeader(value = AUTHORIZATION, required = false) String authorization,
            @PathVariable long id
    ) {
        // Sjekk at bruker i token "eier" innsendte meldeperiode
        log.warn(authorization);

        // Hent data
        String data = mellomlagretMeldeperiodeDAO.hente(id);

        // Svar
        return ResponseEntity.ok(data);
    }
}
