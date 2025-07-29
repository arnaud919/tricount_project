package com.hb.tricount.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ParticipantShareDTO {
    private Long personId;
    private String personName;
    private Long expenseId;
    private String description;
    private BigDecimal amountOwed;
}