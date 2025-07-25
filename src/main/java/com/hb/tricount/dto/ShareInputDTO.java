package com.hb.tricount.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ShareInputDTO {
    private Long personId;
    private BigDecimal amountOwed;
}
