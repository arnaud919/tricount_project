package com.hb.tricount.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "group_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "group_person", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> members;

    @OneToMany(mappedBy = "group")
    private List<Expense> expenses;

    @OneToMany(mappedBy = "group")
    private List<Payment> payments;

    @OneToMany(mappedBy = "group")
    private List<ParticipantShare> shares;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private Person creator;
}