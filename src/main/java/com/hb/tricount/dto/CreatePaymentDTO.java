package com.hb.tricount.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreatePaymentDTO {
    private Long debtorId;      // Personne qui rembourse
    private Long creditorId;        // Personne remboursée
    private BigDecimal amount;
    private LocalDate date;
    private Long groupId;
}