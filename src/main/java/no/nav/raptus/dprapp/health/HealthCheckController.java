package no.nav.raptus.dprapp.health;

import lombok.extern.slf4j.Slf4j;
import no.nav.security.token.support.core.api.Unprotected;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST-controller som brukes av Kubernetes til readiness- og liveness-prober. Konfigureres i naiserator.yaml.
 */

@Slf4j
@Unprotected
@RestController
@RequestMapping(path = "/internal")
public class HealthCheckController {

    private final HealthCheckDbProbe healthCheckDbProbe;

    HealthCheckController(HealthCheckDbProbe healthCheckDbProbe) {
        this.healthCheckDbProbe = healthCheckDbProbe;
    }

    @GetMapping(path = "/isready")
    public String isReady() {
        healthCheckDbProbe.pingDatabase();
        log.debug("/isready");
        return "Ready";
    }
}
