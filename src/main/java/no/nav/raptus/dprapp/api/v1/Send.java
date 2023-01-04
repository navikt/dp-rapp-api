package no.nav.raptus.dprapp.api.v1;

import lombok.extern.slf4j.Slf4j;
import no.nav.raptus.dprapp.model.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class Send {

    @PostMapping(path = "/send")
    public ResponseEntity<String> send(@RequestBody Data data) {
        // Sjekk at bruker i token "eier" innsendte meldekortet

        // Kontroller

        // Slett mellomlagret meldeperiode

        // Svar
        return ResponseEntity.ok().build();
    }
}
