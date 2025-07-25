package com.hb.tricount.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ExpenseDTO {
    private Long id;
    private String description;
    private BigDecimal amount;
    private LocalDate date;
    private Long payerId;
    private Long groupId;
    private List<ParticipantShareDTO> shares;
}
