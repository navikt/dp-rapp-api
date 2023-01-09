package no.nav.raptus.dprapp.common.logging;

import lombok.extern.slf4j.Slf4j;
import no.nav.raptus.dprapp.common.mdc.MdcOperations;
import no.nav.raptus.dprapp.db.entity.KallLogg;
import no.nav.raptus.dprapp.db.repository.KallLoggDAO;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static no.nav.raptus.dprapp.Utils.formatHeaders;

/**
 * Spring ClientHttpRequestInterceptor for logging av HTTP request- og respons for konsumerte REST-tjenester til
 * KALL_LOGG-tabellen.
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
 */
@Slf4j
public class HttpLoggingInterceptor implements ClientHttpRequestInterceptor {

    private final KallLoggDAO kallLoggDAO;

    public HttpLoggingInterceptor(KallLoggDAO kallLoggDAO) {
        this.kallLoggDAO = kallLoggDAO;
    }

    @NotNull
    @Override
    public ClientHttpResponse intercept(
            @NotNull HttpRequest request,
            @NotNull byte[] body,
            ClientHttpRequestExecution execution
    ) throws IOException {
        String formattedRequest = formatRequest(request, body);

        long startTime = System.currentTimeMillis();

        ClientHttpResponse response = null;

        try {
            response = execution.execute(request, body);
        } finally {
            long endTime = System.currentTimeMillis();

            String formattedResponse = null;
            if (response != null) {
                formattedResponse = formatResponse(response);
            }

            KallLogg kallLogg = KallLogg.builder() //
                    .korrelasjonId(MdcOperations.get(MdcOperations.MDC_CORRELATION_ID)) //
                    .tidspunkt(LocalDateTime.now()) //
                    .type(KallLogg.TYPE_REST) //
                    .retning(KallLogg.RETNING_UT) //
                    .metode(request.getMethod().name()) //
                    .path(request.getURI().getPath()) //
                    .status((response != null) ? response.getStatusCode().value() : null) //
                    .kalltid(endTime - startTime) //
                    .request(formattedRequest) //
                    .response(formattedResponse) //
                    .build();

            log.debug(kallLogg.toString());

            saveKallLogg(kallLogg);
        }

        return response;
    }

    //
    // Format request
    //

    private String formatRequest(HttpRequest request, byte[] body) {
        StringBuilder builder = new StringBuilder();
        formatMethodAndRequestURL(builder, request);
        formatHeaders(builder, request.getHeaders());
        formatBody(builder, body);

        return builder.toString();
    }

    private void formatMethodAndRequestURL(StringBuilder builder, HttpRequest request) {
        builder.append(request.getMethod().name()).append(" ").append(request.getURI()).append("\n");
    }

    private void formatBody(StringBuilder builder, byte[] body) {
        if (body != null) {
            String bodyAsString = new String(body, StandardCharsets.UTF_8);
            formatBody(builder, bodyAsString);
        }
    }

    //
    // Format response
    //

    private String formatResponse(ClientHttpResponse response) throws IOException {
        StringBuilder builder = new StringBuilder();
        formatStatus(builder, response);
        formatHeaders(builder, response.getHeaders());
        formatBody(builder, response);

        return builder.toString();
    }

    private void formatStatus(StringBuilder builder, ClientHttpResponse response) throws IOException {
        builder.append("HTTP ").append(response.getStatusCode().value()).append(" ").append(response.getStatusText()).append("\n");
    }

    private void formatBody(StringBuilder builder, ClientHttpResponse response) throws IOException {
        String bodyAsString = readInputStreamAsString(response.getBody());
        formatBody(builder, bodyAsString);
    }

    private String readInputStreamAsString(InputStream input) throws IOException {
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }

        return textBuilder.toString();
    }

    //
    // Felles
    //

    private void formatBody(StringBuilder builder, String body) {
        if (body.length() > 0) {
            builder.append(body);
            builder.append('\n');
        }
    }

    private void saveKallLogg(KallLogg kallLogg) {
        try {
            kallLoggDAO.create(kallLogg);
        } catch (Exception e) {
            log.error("Feil ved logging av kalloggdata til databasen for konsumert tjeneste. Feilmelding: " + e.getMessage(), e);
        }
    }
}

