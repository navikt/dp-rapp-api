package no.nav.raptus.dprapp.common.mdc;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * Servletfilter som setter MDC-verdier (Mapped Diagnostics Context). MDC-verdiene fjernes igjen ved utgangen av filteret.
 */
public class MdcFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
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