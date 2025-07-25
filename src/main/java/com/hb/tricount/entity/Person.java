package com.hb.tricount.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @ManyToMany(mappedBy = "members")
    private List<Group> groups;

    @OneToMany(mappedBy = "payer")
    private List<Expense> expensesPaid;

    @OneToMany(mappedBy = "person")
    private List<ParticipantShare> shares;

    @OneToMany(mappedBy = "creditor")
    private List<Payment> paymentsReceived;

    @OneToMany(mappedBy = "debtor")
    private List<Payment> paymentsMade;
}
