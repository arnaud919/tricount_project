package com.hb.tricount.business.impl;

import com.hb.tricount.business.ExpenseService;
import com.hb.tricount.dto.*;
import com.hb.tricount.entity.*;
import com.hb.tricount.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        @Transactional
        public ExpenseDTO addExpense(CreateExpenseDTO dto) {
                Person payer = personRepository.findById(dto.getPayerId())
                                .orElseThrow(() -> new IllegalArgumentException("Payer not found"));

                Group group = groupRepository.findById(dto.getGroupId())
                                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

                Expense expense = Expense.builder()
                                .description(dto.getDescription())
                                .amount(dto.getAmount())
                                .date(dto.getDate())
                                .payer(payer)
                                .group(group)
                                .build();

                List<ParticipantShare> shares = new ArrayList<>();
                for (ShareInputDTO shareDTO : dto.getShares()) {
                        Person person = personRepository.findById(shareDTO.getPersonId())
                                        .orElseThrow(() -> new IllegalArgumentException("Participant not found"));

                        ParticipantShare share = ParticipantShare.builder()
                                        .expense(expense)
                                        .person(person)
                                        .amountOwed(shareDTO.getAmountOwed())
                                        .build();

                        shares.add(share);
                }

                expense.setShares(shares);
                Expense savedExpense = expenseRepository.save(expense);

                // Pas besoin de sauvegarder les shares séparément : cascade = ALL

                List<ParticipantShareDTO> shareDTOs = shares.stream()
                                .map(s -> ParticipantShareDTO.builder()
                                                .personId(s.getPerson().getId())
                                                .amountOwed(s.getAmountOwed())
                                                .build())
                                .toList();

                return ExpenseDTO.builder()
                                .id(savedExpense.getId())
                                .description(savedExpense.getDescription())
                                .amount(savedExpense.getAmount())
                                .date(savedExpense.getDate())
                                .payerId(savedExpense.getPayer().getId())
                                .groupId(savedExpense.getGroup().getId())
                                .shares(shareDTOs)
                                .build();
        }

        @Override
        public List<ExpenseDTO> getExpensesByGroupId(Long groupId) {
                List<Expense> expenses = expenseRepository.findByGroupId(groupId);

                return expenses.stream().map(expense -> {
                        List<ParticipantShareDTO> shares = expense.getShares().stream()
                                        .map(s -> ParticipantShareDTO.builder()
                                                        .personId(s.getPerson().getId())
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

}
