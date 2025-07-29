package com.hb.tricount.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class ExpenseDTO {
    private Long id; // ✅ À ajouter
    private Long expenseId;
    private String description;           // Libellé de la dépense
    private BigDecimal amount;           // Montant total
    private LocalDate date;
    private Long payerId;
    private String payerName;
    private Long groupId;
    private List<ParticipantShareDTO> shares; // Liste des parts
}
