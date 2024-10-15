package com.fresh.coding.sooatelapi;

import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SooatelApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SooatelApiApplication.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(RepositoryFactory factory) {
        return args -> {

        };
    }

}
