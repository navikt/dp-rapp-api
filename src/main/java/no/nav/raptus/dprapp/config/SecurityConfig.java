package no.nav.raptus.dprapp.config;

import no.nav.security.token.support.spring.api.EnableJwtTokenValidation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

// En tom klasse bare for å holde Security konfigurasjon (Annotations).
// ConditionalOnProperty gjør slik at Configuration EnableJwtTokenValidation ikke brukes når bypass.ident.check=true
// Alle Controller'e må ha Protected eller Unprotected annotation når EnableJwtTokenValidation er på.
// Men vi kan ikke sette disse i Swagger sine Controller'e, da bare ignorerer vi disse
// I tillegg ignorerer vi også Spring Framework sine interne Controller'e (dumt at dette må skrives inn manuelt)
@ConditionalOnProperty(
        value= {"bypass.ident.check"},
        havingValue = "false",
        matchIfMissing = true
)
@Configuration
@EnableJwtTokenValidation(ignore = {"org.springframework", "org.springdoc"})
public class SecurityConfig {
}
