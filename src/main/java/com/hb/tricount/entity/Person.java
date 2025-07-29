package com.hb.tricount.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person implements UserDetails {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @Column(nullable = false)
    private String password;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(); // ou des rôles si tu les ajoutes plus tard
    }

    @Override
    public String getUsername() {
        return email;
    }
}
