// package com.hb.tricount.config;

// import com.hb.tricount.entity.*;
// import com.hb.tricount.repository.*;
// import lombok.RequiredArgsConstructor;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Component;

// import java.math.BigDecimal;
// import java.time.LocalDate;
// import java.util.ArrayList;
// import java.util.List;

// @Component
// @RequiredArgsConstructor
// public class DataInitializer implements CommandLineRunner {

//     private final PersonRepository personRepository;
//     private final GroupRepository groupRepository;
//     private final ExpenseRepository expenseRepository;
//     private final ParticipantShareRepository participantShareRepository;
//     private final PasswordEncoder passwordEncoder;

//     @Override
//     public void run(String... args) {
//         // Création de personnes
//         Person alice = personRepository.save(Person.builder()
//                 .name("Alice")
//                 .email("alice@example.com")
//                 .password(passwordEncoder.encode("password"))
//                 .build());

//         Person bob = personRepository.save(Person.builder()
//                 .name("Bob")
//                 .email("bob@example.com")
//                 .password(passwordEncoder.encode("password"))
//                 .build());

//         Person charlie = personRepository.save(Person.builder()
//                 .name("Charlie")
//                 .email("charlie@example.com")
//                 .password(passwordEncoder.encode("password"))
//                 .build());

//         Person arnaud = personRepository.save(Person.builder()
//                 .name("Arnaud")
//                 .email("arnaudtest2@email.com")
//                 .password(passwordEncoder.encode("arnaudtest2"))
//                 .build());

//         // Création du groupe
//         Group group = groupRepository.save(Group.builder()
//                 .name("Voyage Espagne")
//                 .creator(arnaud)
//                 .members(new ArrayList<>(List.of(alice, bob, charlie, arnaud)))
//                 .build());

//         // Dépense de 90€ payée par Alice
//         Expense expense = expenseRepository.save(Expense.builder()
//                 .description("Restaurant")
//                 .amount(BigDecimal.valueOf(90))
//                 .payer(alice)
//                 .group(group)
//                 .date(LocalDate.of(2025, 8, 1))
//                 .build());

//         BigDecimal part = BigDecimal.valueOf(30);

//         participantShareRepository.saveAll(List.of(
//                 ParticipantShare.builder()
//                         .person(bob)
//                         .expense(expense)
//                         .group(group)
//                         .amountOwed(part)
//                         .build(),
//                 ParticipantShare.builder()
//                         .person(charlie)
//                         .expense(expense)
//                         .group(group)
//                         .amountOwed(part)
//                         .build()));
//     }
// }
