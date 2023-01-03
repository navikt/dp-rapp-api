package no.nav.raptus.dprapp.api.v1;

import lombok.extern.slf4j.Slf4j;
import no.nav.raptus.dprapp.model.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class SaveData {

    // Midlertidig "in memory" lagring
    private final Map<String, Data> savedData = new HashMap<>();

    @PostMapping(path = "/save")
    public ResponseEntity<String> save(@RequestBody Data data) {
        // Sjekk at bruker i token "eier" innsendte meldekortet

        // Lagre data
        savedData.put(data.getId(), data);

        // Svar
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/send")
    public ResponseEntity<String> send(@RequestBody Data data) {
        // Sjekk at bruker i token "eier" innsendte meldekortet

        // Kontroller

        // Svar
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Data> get(@PathVariable String id) {
        return ResponseEntity.ok(savedData.get(id));
    }
}
