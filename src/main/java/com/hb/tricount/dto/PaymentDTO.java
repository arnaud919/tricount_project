package com.hb.tricount.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PaymentDTO {
    private Long id;
    private Long fromId;
    private Long toId;
    private BigDecimal amount;
    private LocalDate date;
    private Long groupId;
}
