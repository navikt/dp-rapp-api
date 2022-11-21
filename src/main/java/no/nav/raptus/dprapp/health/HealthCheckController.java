package no.nav.raptus.dprapp.health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST-controller som brukes av Kubernetes til readiness- og liveness-prober. Konfigureres i naiserator.yaml.
 */

@Slf4j
@RestController
@RequestMapping(path = "/internal")
public class HealthCheckController {

    private HealthCheckDbProbe healthCheckDbProbe;

    HealthCheckController(HealthCheckDbProbe healthCheckDbProbe) {
        this.healthCheckDbProbe = healthCheckDbProbe;
    }

    // TODO @Unprotected ?
    @GetMapping(path = "/isready")
    public String isReady() {
        healthCheckDbProbe.pingDatabase();
        log.debug("/isready");
        return "Ready";
    }
}
