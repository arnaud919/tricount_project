package com.hb.tricount.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class CreateExpenseDTO {
    private String description;
    private BigDecimal amount;
    private LocalDate date;
    private Long payerId;
    private Long groupId;
    private List<ShareInputDTO> shares;
    private List<Long> participantIds;
}
