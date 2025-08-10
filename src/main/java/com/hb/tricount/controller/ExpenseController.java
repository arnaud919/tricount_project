package com.hb.tricount.controller;

import com.hb.tricount.business.ExpenseService;
import com.hb.tricount.dto.CreateExpenseDTO;
import com.hb.tricount.dto.ExpenseDTO;

import java.math.BigDecimal;
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

    /**
     * 
     * Ajout d'une dépense dans un groupe
     * 
     * @param dto
     * @return
     */
    @PostMapping
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody CreateExpenseDTO dto) {
        ExpenseDTO created = expenseService.addExpense(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /**
     * 
     * Récupération de la liste des dépenses d'un groupe en particulier
     * 
     * @param groupId
     * @return
     */
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<ExpenseDTO>> getByGroup(@PathVariable Long groupId) {
        List<ExpenseDTO> expenses = expenseService.getExpensesByGroupId(groupId);
        return ResponseEntity.ok(expenses);
    }

    /**
     * 
     * Récupération de la somme total des dépenses d'un groupe
     * 
     * @param groupId
     * @return
     */
    @GetMapping("/group/{groupId}/total")
    public ResponseEntity<BigDecimal> getTotalAmountByGroup(@PathVariable Long groupId) {
        return ResponseEntity.ok(expenseService.getTotalAmountByGroup(groupId));
    }

    /**
     * 
     * Récupérer les dépenses d'un groupe supérieur ou inférieur à une valeur
     * 
     * Dépsenses inférieurs à
     * {{baseURL}}/api/expenses/group/{{group}}/filter?amount={{expense amount}}&type=lt
     * 
     * Dépenses supérieurs à
     * {{baseURL}}/api/expenses/group/{{group}}/filter?amount={{expense amount}}&type=gt
     * 
     * @param groupId
     * @param amount
     * @param type
     * @return
     */
    @GetMapping("/group/{groupId}/filter")
    public List<ExpenseDTO> filterExpensesByAmount(
            @PathVariable Long groupId,
            @RequestParam BigDecimal amount,
            @RequestParam String type) {
        return expenseService.findByGroupAndAmountFilter(groupId, type, amount);
    }


    /**
     * 
     * Récupération de toutes les dépenses d'une personne
     * 
     * @param personId
     * @return
     */
    @GetMapping("/person/{personId}/all")
    public ResponseEntity<List<ExpenseDTO>> getAllExpensesByPerson(@PathVariable Long personId) {
        return ResponseEntity.ok(expenseService.getAllExpensesByPerson(personId));
    }

}
