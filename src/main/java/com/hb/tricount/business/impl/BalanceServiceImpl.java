package com.hb.tricount.business.impl;

import com.hb.tricount.business.BalanceService;
import com.hb.tricount.dto.DebtDTO;
import com.hb.tricount.entity.Expense;
import com.hb.tricount.entity.ParticipantShare;
import com.hb.tricount.entity.Person;
import com.hb.tricount.repository.ExpenseRepository;
import com.hb.tricount.repository.ParticipantShareRepository;
import com.hb.tricount.repository.PersonRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class BalanceServiceImpl implements BalanceService {

    private final ExpenseRepository expenseRepository;
    private final ParticipantShareRepository participantShareRepository;
    private final PersonRepository personRepository;

    public BalanceServiceImpl(ExpenseRepository expenseRepository, ParticipantShareRepository participantShareRepository, PersonRepository personRepository) {
        this.expenseRepository = expenseRepository;
        this.participantShareRepository = participantShareRepository;
        this.personRepository = personRepository;
    }

    @Override
    public List<DebtDTO> getDebtsByGroup(Long groupId) {
        List<ParticipantShare> shares = participantShareRepository.findByGroupId(groupId);

        Map<String, BigDecimal> debtMap = new HashMap<>();

        for (ParticipantShare share : shares) {
            Long creditorId = share.getExpense().getPayer().getId();
            String creditorName = share.getExpense().getPayer().getName();
            Long debtorId = share.getPerson().getId();
            String debtorName = share.getPerson().getName();
            BigDecimal amount = share.getAmountOwed();

            // Ne pas se créer une dette envers soi-même
            if (!creditorId.equals(debtorId)) {
                String key = creditorId + "-" + debtorId;
                debtMap.put(key, debtMap.getOrDefault(key, BigDecimal.ZERO).add(amount));
            }
        }

        // Conversion en DTO
        List<DebtDTO> result = new ArrayList<>();

        for (String key : debtMap.keySet()) {
            String[] parts = key.split("-");
            Long creditorId = Long.valueOf(parts[0]);
            Long debtorId = Long.valueOf(parts[1]);

            // récupération de noms si nécessaire (optionnel si déjà récupéré dans la boucle
            // précédente)
            Person creditor = personRepository.findById(creditorId).orElseThrow();
            Person debtor = personRepository.findById(debtorId).orElseThrow();

            result.add(DebtDTO.builder()
                    .creditorId(creditorId)
                    .creditorName(creditor.getName())
                    .debtorId(debtorId)
                    .debtorName(debtor.getName())
                    .amount(debtMap.get(key))
                    .build());
        }

        return result;
    }

}
