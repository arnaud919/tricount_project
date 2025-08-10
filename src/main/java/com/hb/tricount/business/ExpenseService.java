package com.hb.tricount.business;

import java.math.BigDecimal;
import java.util.List;

import com.hb.tricount.dto.CreateExpenseDTO;
import com.hb.tricount.dto.ExpenseDTO;

public interface ExpenseService {

    ExpenseDTO addExpense(CreateExpenseDTO dto);
    List<ExpenseDTO> getExpensesByGroupId(Long groupId);
    BigDecimal getTotalAmountByGroup(Long groupId);
    List<ExpenseDTO> findByGroupAndAmountFilter(Long groupId, String type, BigDecimal amount);
    List<ExpenseDTO> getAllExpensesByPerson(Long personId);

}
