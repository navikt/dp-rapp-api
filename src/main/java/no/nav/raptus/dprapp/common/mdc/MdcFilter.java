package no.nav.raptus.dprapp.common.mdc;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Servletfilter som setter MDC-verdier (Mapped Diagnostics Context). MDC-verdiene fjernes igjen ved utgangen av filteret.
 */
public class MdcFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            getFromHeaderOrGenerateCorrelationId(request);

            filterChain.doFilter(request, response);
        } finally {
            MdcOperations.remove(MdcOperations.MDC_CORRELATION_ID);
        }
    }

    private void getFromHeaderOrGenerateCorrelationId(HttpServletRequest request) {
        //String correlationId = request.getHeader("X-Request-ID");
        String correlationId = request.getHeader("X-Correlation-ID");

        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
        }

        MdcOperations.put(MdcOperations.MDC_CORRELATION_ID, correlationId);
    }
}