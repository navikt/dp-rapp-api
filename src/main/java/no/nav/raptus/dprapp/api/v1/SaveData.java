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
public class SaveData {

    @PostMapping(path = "/save")
    public ResponseEntity<String> save(@RequestBody Data data) {
        log.warn(data.toString());
        return ResponseEntity.ok().build();
    }
}
