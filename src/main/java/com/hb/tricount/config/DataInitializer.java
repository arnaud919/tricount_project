// package com.hb.tricount.config;

// import com.hb.tricount.entity.Expense;
// import com.hb.tricount.entity.Group;
// import com.hb.tricount.entity.ParticipantShare;
// import com.hb.tricount.entity.Person;
// import com.hb.tricount.repository.ExpenseRepository;
// import com.hb.tricount.repository.GroupRepository;
// import com.hb.tricount.repository.PersonRepository;
// import jakarta.annotation.PostConstruct;
// import org.springframework.stereotype.Component;

// import java.math.BigDecimal;
// import java.time.LocalDate;
// import java.util.Arrays;
// import java.util.List;

// @Component
// public class DataInitializer {

//     private final PersonRepository personRepository;
//     private final GroupRepository groupRepository;
//     private final ExpenseRepository expenseRepository;

//     public DataInitializer(PersonRepository personRepository, GroupRepository groupRepository, ExpenseRepository expenseRepository) {
//         this.personRepository = personRepository;
//         this.groupRepository = groupRepository;
//         this.expenseRepository = expenseRepository;
//     }

//     @PostConstruct
//     public void init() {
//         // Créer des personnes
//         Person hamza = personRepository.save(Person.builder().name("Hamza").email("hamza@example.com").build());
//         Person julie = personRepository.save(Person.builder().name("Julie").email("julie@example.com").build());
//         Person marc = personRepository.save(Person.builder().name("Marc").email("marc@example.com").build());

//         // Créer un groupe avec ces membres
//         Group groupe = Group.builder()
//                 .name("Week-end à Lyon")
//                 .members(Arrays.asList(hamza, julie, marc))
//                 .build();

//         groupRepository.save(groupe);

//         // Recharger le groupe avec les membres sauvegardés (car les Person ont
//         // maintenant un ID)
//         Group savedGroup = groupRepository.findById(groupe.getId()).get();

//         // Dépense 1 : Restaurant
//         Expense restaurant = Expense.builder()
//                 .description("Restaurant")
//                 .amount(new BigDecimal("90"))
//                 .date(LocalDate.of(2025, 7, 21))
//                 .payer(julie)
//                 .group(savedGroup)
//                 .build();

//         ParticipantShare s1 = ParticipantShare.builder().expense(restaurant).person(hamza)
//                 .amountOwed(new BigDecimal("30")).build();
//         ParticipantShare s2 = ParticipantShare.builder().expense(restaurant).person(julie)
//                 .amountOwed(new BigDecimal("30")).build();
//         ParticipantShare s3 = ParticipantShare.builder().expense(restaurant).person(marc)
//                 .amountOwed(new BigDecimal("30")).build();
//         restaurant.setShares(List.of(s1, s2, s3));
//         expenseRepository.save(restaurant);

//         // Dépense 2 : Essence
//         Expense essence = Expense.builder()
//                 .description("Essence")
//                 .amount(new BigDecimal("60"))
//                 .date(LocalDate.of(2025, 7, 22))
//                 .payer(marc)
//                 .group(savedGroup)
//                 .build();

//         ParticipantShare e1 = ParticipantShare.builder().expense(essence).person(hamza).amountOwed(new BigDecimal("30"))
//                 .build();
//         ParticipantShare e2 = ParticipantShare.builder().expense(essence).person(marc).amountOwed(new BigDecimal("30"))
//                 .build();
//         essence.setShares(List.of(e1, e2));
//         expenseRepository.save(essence);
//     }
// }
