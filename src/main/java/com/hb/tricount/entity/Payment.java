package com.hb.tricount.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "creditor_id")
    private Person creditor;

    @ManyToOne
    @JoinColumn(name = "debtor_id")
    private Person debtor;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}
