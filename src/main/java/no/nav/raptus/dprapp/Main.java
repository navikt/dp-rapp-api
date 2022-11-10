package no.nav.raptus.dprapp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println("Username:  " + System.getenv("DB_USERNAME"));
        System.out.println("Password:  " + System.getenv("DB_PASSWORD"));
        System.out.println("------------------------------------------------------------------------------------------------");
        SpringApplication.run(Main.class, args);
    }
}
