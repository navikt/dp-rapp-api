package no.nav.raptus.dprapp.config;

import no.nav.raptus.dprapp.common.logging.HttpLoggingFilter;
import no.nav.raptus.dprapp.common.mdc.MdcFilter;
import no.nav.raptus.dprapp.db.repository.KallLoggDAO;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * Konfigurasjonsklasse som oppretter servletfiltre for HTTP logging og MDC (Mapped Diagnostics Context).
 * <p>
 * Filterene tilordnes høyest og nesthøyest prioritet slik at de kjøres tidlig i filterkjeden. (Høyeste prioritet har laveste
 * verdi.)
 */
@Configuration
public class HttpLoggingConfig {

    @Bean
    public FilterRegistrationBean<MdcFilter> mdcFilterRegistrationBean() {
        FilterRegistrationBean<MdcFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MdcFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<HttpLoggingFilter> httpLoggingFilterRegistrationBean(KallLoggDAO kallLoggDAO) {
        FilterRegistrationBean<HttpLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new HttpLoggingFilter(kallLoggDAO));
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return registrationBean;
    }
}
