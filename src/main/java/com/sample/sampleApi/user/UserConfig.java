package com.sample.sampleApi.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            var jane = new User("Jane", "De Leon", "jane@gmail.com");
            var juan = new User("Juan", "Dela Cruz", "juan@gmail.com");

            userRepository.saveAll(List.of(juan, jane));

        };
    }
}
