package com.hb.tricount.business.impl;

import com.hb.tricount.business.ExpenseService;
import com.hb.tricount.dto.*;
import com.hb.tricount.entity.*;
import com.hb.tricount.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ExpenseServiceImpl implements ExpenseService {

        private final ExpenseRepository expenseRepository;
        private final PersonRepository personRepository;
        private final GroupRepository groupRepository;
        private final ParticipantShareRepository participantShareRepository;

        public ExpenseServiceImpl(
                        ExpenseRepository expenseRepository,
                        PersonRepository personRepository,
                        GroupRepository groupRepository,
                        ParticipantShareRepository participantShareRepository) {
                this.expenseRepository = expenseRepository;
                this.personRepository = personRepository;
                this.groupRepository = groupRepository;
                this.participantShareRepository = participantShareRepository;
        }

        @Override
        public ExpenseDTO addExpense(CreateExpenseDTO dto) {
                Person payer = personRepository.findById(dto.getPayerId())
                                .orElseThrow(() -> new RuntimeException("Payer not found"));
                Group group = groupRepository.findById(dto.getGroupId())
                                .orElseThrow(() -> new RuntimeException("Group not found"));

                Expense expense = Expense.builder()
                                .description(dto.getDescription())
                                .amount(dto.getAmount())
                                .date(dto.getDate())
                                .payer(payer)
                                .group(group)
                                .build();

                Expense savedExpense = expenseRepository.save(expense);

                BigDecimal shareAmount = dto.getAmount().divide(BigDecimal.valueOf(dto.getParticipantIds().size()),
                                RoundingMode.HALF_UP);

                List<ParticipantShare> shares = dto.getParticipantIds().stream()
                                .map(id -> {
                                        Person person = personRepository.findById(id)
                                                        .orElseThrow(() -> new RuntimeException("Person not found"));
                                        return ParticipantShare.builder()
                                                        .person(person)
                                                        .expense(savedExpense)
                                                        .group(group)
                                                        .amountOwed(shareAmount)
                                                        .build();
                                }).toList();

                participantShareRepository.saveAll(shares);

                return ExpenseDTO.builder()
                                .id(savedExpense.getId())
                                .description(savedExpense.getDescription())
                                .amount(savedExpense.getAmount())
                                .date(savedExpense.getDate())
                                .payerId(payer.getId())
                                .groupId(group.getId())
                                .shares(null)
                                .build();
        }

        @Override
        public List<ExpenseDTO> getExpensesByGroupId(Long groupId) {
                List<Expense> expenses = expenseRepository.findByGroupId(groupId);

                return expenses.stream().map(expense -> {
                        List<ParticipantShareDTO> shares = expense.getShares().stream()
                                        .map(s -> ParticipantShareDTO.builder()
                                                        .personId(s.getPerson().getId())
                                                        .personName(s.getPerson().getName())
                                                        .expenseId(s.getExpense().getId())
                                                        .description(s.getExpense().getDescription())
                                                        .amountOwed(s.getAmountOwed())
                                                        .build())
                                        .toList();

                        return ExpenseDTO.builder()
                                        .id(expense.getId())
                                        .description(expense.getDescription())
                                        .amount(expense.getAmount())
                                        .date(expense.getDate())
                                        .payerId(expense.getPayer().getId())
                                        .groupId(expense.getGroup().getId())
                                        .shares(shares)
                                        .build();
                }).toList();
        }

        private ExpenseDTO mapToDTO(Expense expense) {
                List<ParticipantShareDTO> shares = expense.getShares().stream()
                                .map(s -> ParticipantShareDTO.builder()
                                                .personId(s.getPerson().getId())
                                                .personName(s.getPerson().getName())
                                                .expenseId(expense.getId())
                                                .description(expense.getDescription())
                                                .amountOwed(s.getAmountOwed())
                                                .build())
                                .toList();

                return ExpenseDTO.builder()
                                .id(expense.getId())
                                .description(expense.getDescription())
                                .amount(expense.getAmount())
                                .date(expense.getDate())
                                .payerId(expense.getPayer().getId())
                                .groupId(expense.getGroup().getId())
                                .shares(shares)
                                .build();
        }

        @Override
        public BigDecimal getTotalAmountByGroup(Long groupId) {
                throw new UnsupportedOperationException("Unimplemented method 'getTotalAmountByGroup'");
        }

        @Override
        public List<ExpenseDTO> findByGroupAndAmountFilter(Long groupId, String type, BigDecimal amount) {
                List<Expense> expenses = expenseRepository.findByGroupId(groupId);

                return expenses.stream()
                                .filter(e -> {
                                        if ("gt".equalsIgnoreCase(type)) {
                                                return e.getAmount().compareTo(amount) > 0;
                                        } else if ("lt".equalsIgnoreCase(type)) {
                                                return e.getAmount().compareTo(amount) < 0;
                                        }
                                        return true;
                                })
                                .map(this::mapToDTO)
                                .toList();
        }

        @Override
        public List<ExpenseDTO> getAllExpensesByPerson(Long personId) {
                // Dépenses payées par la personne
                List<Expense> paidExpenses = expenseRepository.findByPayer_Id(personId);

                // Dépenses où la personne est bénéficiaire
                List<ParticipantShare> shares = participantShareRepository.findByPersonId(personId);
                List<Expense> benefitedExpenses = shares.stream()
                                .map(ParticipantShare::getExpense)
                                .toList();

                // On combine les deux listes, sans doublon
                Set<Expense> allExpenses = new HashSet<>();
                allExpenses.addAll(paidExpenses);
                allExpenses.addAll(benefitedExpenses);

                return allExpenses.stream()
                                .map(expense -> ExpenseDTO.builder()
                                                .id(expense.getId())
                                                .description(expense.getDescription())
                                                .amount(expense.getAmount())
                                                .date(expense.getDate())
                                                .payerId(expense.getPayer().getId())
                                                .groupId(expense.getGroup().getId())
                                                .build())
                                .toList();
        }

}
