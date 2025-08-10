package com.hb.tricount.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class DebtDTO {
    private Long creditorId;
    private String creditorName;
    private Long debtorId;
    private String debtorName;
    private BigDecimal amount;

    // Constructeur pour le getSharesByGroupId
    public DebtDTO(Long debtorId, Long creditorId, BigDecimal amount) {
        this.debtorId = debtorId;
        this.creditorId = creditorId;
        this.amount = amount;
    }
}