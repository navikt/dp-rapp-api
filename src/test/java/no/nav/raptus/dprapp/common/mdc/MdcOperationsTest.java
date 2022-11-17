package no.nav.raptus.dprapp.common.mdc;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Enhetstester for {@link MdcOperations}.
 */
public class MdcOperationsTest {

    @Test
    public void mdcOperationsOnCorrelationIdShouldSucceed() {
        String correlationId = UUID.randomUUID().toString();
        MdcOperations.put(MdcOperations.MDC_CORRELATION_ID, correlationId);

        assertEquals(MdcOperations.get(MdcOperations.MDC_CORRELATION_ID), correlationId);

        MdcOperations.remove(MdcOperations.MDC_CORRELATION_ID);

        assertNull(MdcOperations.get(MdcOperations.MDC_CORRELATION_ID));
    }
}
