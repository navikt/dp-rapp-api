package no.nav.raptus.dprapp.api.v1;

import lombok.extern.slf4j.Slf4j;
import no.nav.raptus.dprapp.db.repository.MellomlagretMeldeperiodeDAO;
import no.nav.raptus.dprapp.model.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class Mellomlagring {

    @Autowired
    MellomlagretMeldeperiodeDAO mellomlagretMeldeperiodeDAO;

    @PostMapping(path = "/lagre")
    public ResponseEntity<String> lagre(@RequestBody Data data) {
        // Sjekk at bruker i token "eier" innsendte meldeperiode

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
    public ResponseEntity<String> hente(@PathVariable long id) {
        // Sjekk at bruker i token "eier" innsendte meldeperiode

        // Hent data
        String data = mellomlagretMeldeperiodeDAO.hente(id);

        // Svar
        return ResponseEntity.ok(data);
    }
}
