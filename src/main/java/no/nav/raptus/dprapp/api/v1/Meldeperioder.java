package no.nav.raptus.dprapp.api.v1;

import lombok.extern.slf4j.Slf4j;
import no.nav.raptus.dprapp.Utils;
import no.nav.raptus.dprapp.db.entity.Meldeperiode;
import no.nav.raptus.dprapp.db.repository.MeldeperioderDAO;
import no.nav.raptus.dprapp.model.Data;
import no.nav.security.token.support.core.api.Protected;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static no.nav.raptus.dprapp.Konstanter.API_PATH;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Protected
@RestController
@RequestMapping(API_PATH + "/meldeperioder")
public class Meldeperioder {

    @Autowired
    MeldeperioderDAO meldeperioderDAO;

    @GetMapping("/")
    public ResponseEntity<List<Meldeperiode>> hentListe(
            @RequestHeader(value = AUTHORIZATION, required = false) String authorization
    ) {
        log.warn(authorization);
        String fnr = Utils.getIdent(authorization);

        // Hent data
        List<Meldeperiode> meldeperioder = meldeperioderDAO.hentListe(fnr);

        // Svar
        return ResponseEntity.ok(meldeperioder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meldeperiode> hent(
            @RequestHeader(value = AUTHORIZATION, required = false) String authorization,
            @PathVariable long id
    ) {
        // Sjekk at bruker i token "eier" denne meldeperioden

        // Hent data
        Meldeperiode meldeperiode = meldeperioderDAO.hent(id);

        // Svar
        return ResponseEntity.ok(meldeperiode);
    }

    @GetMapping("/mellomlagret/{id}")
    public ResponseEntity<String> hentMellomlagret(
            @RequestHeader(value = AUTHORIZATION, required = false) String authorization,
            @PathVariable long id
    ) {
        // Sjekk at bruker i token "eier" denne meldeperioden

        // Hent data
        // String meldeperiode = meldeperioderDAO.hentMellomlagret(id);
        // TODO: rollback
        long fnr = Long.parseLong(Utils.getIdent(authorization));
        String meldeperiode = meldeperioderDAO.hentMellomlagret(fnr);

        // Svar
        return ResponseEntity.ok(meldeperiode);
    }

    @PostMapping(path = "/lagre")
    public ResponseEntity<String> lagre(
            @RequestHeader(value = AUTHORIZATION, required = false) String authorization,
            @RequestBody Data data
    ) {
        // Sjekk at bruker i token "eier" denne meldeperioden

        // Lagre data
        // meldeperioderDAO.lagre(data.getId(), data.toString()); // overriden data.toString() returns serialized object
        // TODO: rollback
        long fnr = Long.parseLong(Utils.getIdent(authorization));

        meldeperioderDAO.lagre(fnr, data.toString());

        // Svar
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/send")
    public ResponseEntity<String> send(
            @RequestHeader(value = AUTHORIZATION, required = false) String authorization,
            @RequestBody Data data
    ) {
        // Sjekk at bruker i token "eier" denne meldeperioden

        // Kontroller

        // Slett mellomlagret meldeperiode
        // meldeperioderDAO.slett(data.getId());
        // TODO: rollback
        long fnr = Long.parseLong(Utils.getIdent(authorization));
        meldeperioderDAO.slett(fnr);

        // Svar
        return ResponseEntity.ok().build();
    }
}
