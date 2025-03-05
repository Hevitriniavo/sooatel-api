package com.fresh.coding.sooatelapi;

import com.fresh.coding.sooatelapi.entities.Role;
import com.fresh.coding.sooatelapi.entities.User;
import com.fresh.coding.sooatelapi.enums.RoleName;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootApplication
public class SooatelApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SooatelApiApplication.class, args);
    }

    @Bean
    @Transactional
    CommandLineRunner commandLineRunner(RepositoryFactory repositoryFactory, PasswordEncoder passwordEncoder) {
        return args -> {
            var userRepo = repositoryFactory.getUserRepository();
            var roleRepo = repositoryFactory.getRoleRepository();
            var adminRole = roleRepo.findByName(RoleName.ADMIN)
                    .orElseGet(() -> roleRepo.save(Role.builder()
                            .name(RoleName.ADMIN).build()));

            if (userRepo.findByEmail("hei.tantely@gmail.com").isEmpty()) {
                var admin = User.builder()
                        .username("Hevitriniavo")
                        .email("hei.tantely@gmail.com")
                        .password(passwordEncoder.encode("2022$Hevitriniavo$2022"))
                        .roles(List.of(adminRole))
                        .build();
                userRepo.save(admin);
                System.out.println("Utilisateur ADMIN ajouté !");
            } else {
                System.out.println("Utilisateur déjà existant !");
            }
        };
    }
}
