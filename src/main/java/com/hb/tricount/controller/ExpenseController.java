package com.hb.tricount.controller;

import com.hb.tricount.business.ExpenseService;
import com.hb.tricount.dto.CreateExpenseDTO;
import com.hb.tricount.dto.ExpenseDTO;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody CreateExpenseDTO dto) {
        ExpenseDTO created = expenseService.addExpense(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<ExpenseDTO>> getByGroup(@PathVariable Long groupId) {
        List<ExpenseDTO> expenses = expenseService.getExpensesByGroupId(groupId);
        return ResponseEntity.ok(expenses);
    }
}
