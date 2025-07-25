package com.hb.tricount.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DebtDTO {
    private Long from;     // ID de la personne qui doit
    private Long to;       // ID du créancier
    private BigDecimal amount;
}