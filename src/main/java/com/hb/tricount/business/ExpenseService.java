package com.hb.tricount.business;

import java.util.List;

import com.hb.tricount.dto.CreateExpenseDTO;
import com.hb.tricount.dto.ExpenseDTO;

public interface ExpenseService {

    ExpenseDTO addExpense(CreateExpenseDTO dto);
    List<ExpenseDTO> getExpensesByGroupId(Long groupId);

}
