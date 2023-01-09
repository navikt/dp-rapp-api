package no.nav.raptus.dprapp.common.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import no.nav.raptus.dprapp.common.mdc.MdcOperations;
import no.nav.raptus.dprapp.db.entity.KallLogg;
import no.nav.raptus.dprapp.db.repository.KallLoggDAO;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static no.nav.raptus.dprapp.Utils.formatHeaders;

/**
 * Servletfilter for logging av HTTP request- og response for tilbudte REST-tjenester til KALL_LOGG-tabellen.
 * <p>
 * Requestdata som logges er:
 * <ul>
 * <li>HTTP-metode og URI</li>
 * <li>HTTP-headere</li>
 * <li>Data i HTTP-bodyen</li>
 * </ul>
 * Responsdata som logges er:
 * <ul>
 * <li>HTTP statuskode og tekst</li>
 * <li>HTTP-headere</li>
 * <li>Data i HTTP-bodyen</li>
 * </ul>
 * <p>
 * Loggfilteret er inspirert av Spring AbstractRequestLoggingFilter.
 */
@Slf4j
public class HttpLoggingFilter extends OncePerRequestFilter {

    private final KallLoggDAO kallLoggDAO;

    public HttpLoggingFilter(KallLoggDAO kallLoggDAO) {
        this.kallLoggDAO = kallLoggDAO;
    }

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {

        long startTime = System.currentTimeMillis();

        HttpServletRequest requestToUse = request;
        if (!(request instanceof ContentCachingRequestWrapper)) {
            requestToUse = new ContentCachingRequestWrapper(request);
        }

        HttpServletResponse responseToUse = response;
        if (!(response instanceof ContentCachingResponseWrapper)) {
            responseToUse = new ContentCachingResponseWrapper(response);
        }

        try {
            filterChain.doFilter(requestToUse, responseToUse);
        } finally {
            String formattedRequest = formatRequest(requestToUse);
            String formattedResponse = formatResponse(responseToUse);

            long endTime = System.currentTimeMillis();

            KallLogg kallLogg = KallLogg.builder()
                    .korrelasjonId(MdcOperations.get(MdcOperations.MDC_CORRELATION_ID))
                    .tidspunkt(LocalDateTime.now())
                    .type(KallLogg.TYPE_REST)
                    .retning(KallLogg.RETNING_INN)
                    .metode(requestToUse.getMethod())
                    .path(requestToUse.getRequestURI())
                    .status(responseToUse.getStatus())
                    .kalltid(endTime - startTime)
                    .request(formattedRequest)
                    .response(formattedResponse)
                    .build();

            log.debug(kallLogg.toString());

            saveKallLogg(kallLogg);
        }
    }

    //
    // Format request
    //

    private String formatRequest(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder();
        formatMethodAndRequestURI(builder, request);
        formatHeaders(builder, getHeaders(request));
        formatBody(builder, request);

        return builder.toString();
    }

    private void formatMethodAndRequestURI(StringBuilder builder, HttpServletRequest request) {
        builder.append(request.getMethod()).append(' ').append(request.getRequestURI());

        String queryString = request.getQueryString();
        if (queryString != null) {
            builder.append('?').append(queryString);
        }

        builder.append('\n');
    }

    private HttpHeaders getHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();

        for (String headerName : Collections.list(request.getHeaderNames())) {
            headers.addAll(headerName, Collections.list(request.getHeaders(headerName)));
        }
        return headers;
    }

    private void formatBody(StringBuilder builder, HttpServletRequest request) {
        ContentCachingRequestWrapper wrappedRequest = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrappedRequest == null) {
            return;
        }

        byte[] buf = wrappedRequest.getContentAsByteArray();
        if (buf.length > 0) {
            String payload;

            try {
                payload = new String(buf, 0, buf.length, wrappedRequest.getCharacterEncoding());
            } catch (IOException e) {
                payload = "[unknown]";
            }

            builder.append(payload);
        }
    }

    //
    // Format response
    //

    private String formatResponse(HttpServletResponse response) {
        StringBuilder builder = new StringBuilder();
        formatStatus(builder, response);
        formatHeaders(builder, getHeaders(response));
        formatBody(builder, response);

        return builder.toString();
    }

    private void formatStatus(StringBuilder builder, HttpServletResponse response) {
        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
        builder.append("HTTP ").append(httpStatus.value()).append(' ').append(httpStatus.getReasonPhrase()).append('\n');
    }

    private HttpHeaders getHeaders(HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();

        for (String headerName : response.getHeaderNames()) {
            headers.addAll(headerName, new ArrayList<>(response.getHeaders(headerName)));
        }
        return headers;
    }

    private void formatBody(StringBuilder builder, HttpServletResponse response) {
        ContentCachingResponseWrapper wrappedResponse = WebUtils.getNativeResponse(response,
                ContentCachingResponseWrapper.class);
        if (wrappedResponse == null) {
            return;
        }

        byte[] buf = wrappedResponse.getContentAsByteArray();
        if (buf.length > 0) {
            String payload;

            try {
                payload = new String(buf, 0, buf.length, wrappedResponse.getCharacterEncoding());

                // Viktig! Ellers blir det ingen responsdata igjen å returnere til konsumenten...
                wrappedResponse.copyBodyToResponse();
            } catch (IOException e) {
                payload = "[unknown]";
            }

            builder.append(payload);
        }
    }

    private void saveKallLogg(KallLogg kallLogg) {
        try {
            kallLoggDAO.create(kallLogg);
        } catch (Exception e) {
            log.error("Feil ved logging av kalloggdata til databasen for tilbudt tjeneste. Feilmelding: " + e.getMessage(), e);
        }
    }
}
