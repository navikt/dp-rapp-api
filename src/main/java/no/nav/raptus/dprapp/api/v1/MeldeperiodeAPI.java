package no.nav.raptus.dprapp.api.v1;

import lombok.extern.slf4j.Slf4j;
import no.nav.raptus.dprapp.db.entity.Meldeperiode;
import no.nav.security.token.support.core.api.Protected;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static no.nav.raptus.dprapp.Konstanter.API_PATH;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Protected
@RestController
@RequestMapping(API_PATH + "/meldeperiode")
public class MeldeperiodeAPI {

    @GetMapping("/hente")
    public ResponseEntity<List<Meldeperiode>> hente(@RequestHeader(value = AUTHORIZATION, required = false) String authorization) {
        // Sjekk at bruker i token "eier" innsendte meldeperiode
        log.warn(authorization);

        // Hent data
        List<Meldeperiode> meldeperioder = new ArrayList<>();
        meldeperioder.add(
                Meldeperiode.builder()
                        .meldeperiodeId(1L)
                        .fomDato(LocalDate.of(2022, 11, 27))
                        .tomDato(LocalDate.of(2022, 12, 4))
                        .frist(LocalDate.of(2022, 12, 5))
                        .build()
        );
        meldeperioder.add(
                Meldeperiode.builder()
                        .meldeperiodeId(2L)
                        .fomDato(LocalDate.of(2022, 12, 5))
                        .tomDato(LocalDate.of(2022, 12, 18))
                        .frist(LocalDate.of(2022, 12, 19))
                        .build()
        );
        meldeperioder.add(
                Meldeperiode.builder()
                        .meldeperiodeId(3L)
                        .fomDato(LocalDate.of(2022, 12, 19))
                        .tomDato(LocalDate.of(2023, 1, 1))
                        .frist(LocalDate.of(2023, 1, 2))
                        .build()
        );

        // Svar
        return ResponseEntity.ok(meldeperioder);
    }
}
