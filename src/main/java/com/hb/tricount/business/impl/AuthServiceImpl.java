package com.hb.tricount.business.impl;

import com.hb.tricount.business.AuthService;
import com.hb.tricount.dto.AuthResponseDTO;
import com.hb.tricount.dto.LoginDTO;
import com.hb.tricount.dto.RegisterDTO;
import com.hb.tricount.entity.Person;
import com.hb.tricount.repository.PersonRepository;
import com.hb.tricount.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(PersonRepository personRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponseDTO register(RegisterDTO dto) {
        if (personRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        Person person = Person.builder()
                .name(dto.getFirstname() + " " + dto.getLastname())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        personRepository.save(person);

        String token = jwtService.generateToken(person);
        return AuthResponseDTO.builder().token(token).build();
    }

    @Override
    public AuthResponseDTO login(LoginDTO dto) {
        Person person = personRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(dto.getPassword(), person.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = jwtService.generateToken(person);
        return AuthResponseDTO.builder().token(token).build();
    }
}
