package no.nav.raptus.dprapp.health;

import no.nav.raptus.dprapp.db.repository.KallLoggDAO;
import org.springframework.stereotype.Component;

/**
 * Helsesjekk som brukes for å sjekke at databasen er tilgjengelig for applikasjonen.
 */
@Component
public class HealthCheckDbProbe {

    private KallLoggDAO kallLoggDAO;

    HealthCheckDbProbe(KallLoggDAO kallLoggDAO) {
        this.kallLoggDAO = kallLoggDAO;
    }

    /**
     * Pinger databasen ved å forsøke en spørring mot kall_logg-tabellen, men henter ingen data.
     */
    public void pingDatabase() {
        kallLoggDAO.pingKallLogg();
    }
}
