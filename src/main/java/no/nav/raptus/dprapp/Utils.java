package no.nav.raptus.dprapp;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {

    public static String getIdent(String authString) {
        if ("true".equals(System.getenv("BYPASS_SECURITY"))) {
            return System.getenv("TEST_IDENT");
        }

        DecodedJWT decodedToken = decodeToken(authString);

        return extractSubject(decodedToken);
    }

    private static DecodedJWT decodeToken(String authString) {
        if (authString == null) {
            return null;
        }

        String token = authString.replace("Bearer ", "");

        if (token.isBlank()) {
            return null;
        }

        DecodedJWT decodedToken = null;

        try {
            decodedToken = JWT.decode(token);
        } catch (Exception e) {
            log.warn("Kunne ikke dekode token.", e);
        }

        return decodedToken;
    }

    private static String extractSubject(DecodedJWT decodedToken) {
        if (decodedToken == null) {
            return null;
        }

        Claim pid = decodedToken.getClaim("pid");
        Claim sub = decodedToken.getClaim("sub");

        if (pid != null) {
            return pid.asString();
        } else if (sub != null) {
            return sub.asString();
        }

        return null;
    }
}
