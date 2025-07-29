package com.hb.tricount.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.hb.tricount.repository.PersonRepository;

@Configuration
public class UserDetailsServiceConfig {

    private final PersonRepository personRepository;

    public UserDetailsServiceConfig(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> personRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }
}

