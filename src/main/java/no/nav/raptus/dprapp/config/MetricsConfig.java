package no.nav.raptus.dprapp.config;


import io.micrometer.core.instrument.Clock;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.CollectorRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Konfigurasjonsklasse for skraping av metrikker med Prometheus.
 */
@Configuration
public class MetricsConfig {

    /**
     * @param config            konfigurasjon for registeret.
     * @param collectorRegistry samler opp metrikker til Prometheus-registeret.
     * @param clock             systemklokke for registeret.
     * @return Et Prometheus-register. Metrikkene som applikasjonen skal logge blir lagret her.
     */
    @Bean
    public PrometheusMeterRegistry prometheusMeterRegistry(PrometheusConfig config, CollectorRegistry collectorRegistry,
                                                           Clock clock) {

        return new PrometheusMeterRegistry(config, collectorRegistry, clock);
    }

    /**
     * @return Konfigurasjon som brukes i prometheusMeterRegistry.
     */
    @Bean
    public PrometheusConfig prometheusConfig() {
        return PrometheusConfig.DEFAULT;
    }

    /**
     * @return Register som samler opp metrikker til Prometheus.
     */
    @Bean
    public CollectorRegistry collectorRegistry() {
        return new CollectorRegistry(true);
    }

    /**
     * @return Systemklokke som brukes i prometheusMeterRegistry.
     */
    @Bean
    public Clock micrometerClock() {
        return Clock.SYSTEM;
    }
}
