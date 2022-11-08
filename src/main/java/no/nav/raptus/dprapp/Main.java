package no.nav.raptus.dprapp;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import no.nav.raptus.dprapp.api.Ping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@SpringBootApplication
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info(System.getenv("DB_PASSWORD"));
        logger.info(System.getenv("DB_USERNAME"));
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public DataSource dataSource() {

        String url = "jdbc:postgresql://" + System.getenv("DB_HOST") +
                ":" + System.getenv("DB_PORT") +
                "/" + System.getenv("DB_DATABASE");


        Properties properties = new Properties();
        properties.put("driverClassName", "org.postgresql.Driver");
        properties.put("username", System.getenv("DB_USERNAME"));
        properties.put("password", System.getenv("DB_PASSWORD"));
        properties.put("jdbcUrl", url);

        return new HikariDataSource(new HikariConfig(properties));
    }
}
