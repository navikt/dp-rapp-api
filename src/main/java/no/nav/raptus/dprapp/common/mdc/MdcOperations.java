package no.nav.raptus.dprapp.common.mdc;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

/**
 * Hjelpeklasse for MDC-operasjoner (Mapped Diagnostics Context).
 */
@Slf4j
public class MdcOperations {

    /**
     * Nøkkelverdi for korrelasjonsid i MDC'en.
     */
    public static final String MDC_CORRELATION_ID = "correlationId";

    private MdcOperations() {
    }

    public static String get(String key) {
        return MDC.get(key);
    }

    public static void put(String key, String value) {
        MDC.put(key, value);

        log.debug("Setter MDC-verdi {}={}", key, value);
    }

    public static void remove(String key) {
        MDC.remove(key);
    }
}
