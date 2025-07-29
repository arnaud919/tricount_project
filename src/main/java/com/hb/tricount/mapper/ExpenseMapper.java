package com.hb.tricount.mapper;

import com.hb.tricount.dto.CreateExpenseDTO;
import com.hb.tricount.entity.Expense;
import com.hb.tricount.entity.Group;
import com.hb.tricount.entity.Person;

public class ExpenseMapper {

    public static Expense toEntity(CreateExpenseDTO dto, Group group, Person payer) {
        return Expense.builder()
                .description(dto.getDescription())
                .amount(dto.getAmount())
                .date(dto.getDate())
                .group(group)
                .payer(payer)
                .build();
    }
}
