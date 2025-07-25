package com.hb.tricount.business.impl;

import com.hb.tricount.business.BalanceService;
import com.hb.tricount.dto.DebtDTO;
import com.hb.tricount.entity.Expense;
import com.hb.tricount.entity.ParticipantShare;
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
    private final ParticipantShareRepository shareRepository;

    public BalanceServiceImpl(
            ExpenseRepository expenseRepository,
            ParticipantShareRepository shareRepository
    ) {
        this.expenseRepository = expenseRepository;
        this.shareRepository = shareRepository;
    }

    @Override
    @Transactional
    public List<DebtDTO> calculateGroupBalance(Long groupId) {
        // 1. Initialiser les soldes
        Map<Long, BigDecimal> paid = new HashMap<>();
        Map<Long, BigDecimal> owed = new HashMap<>();

        List<Expense> expenses = expenseRepository.findByGroupId(groupId);

        for (Expense expense : expenses) {
            Long payerId = expense.getPayer().getId();
            BigDecimal amount = expense.getAmount();

            // Ajout à la somme payée
            paid.put(payerId, paid.getOrDefault(payerId, BigDecimal.ZERO).add(amount));

            // Répartition des parts
            for (ParticipantShare share : expense.getShares()) {
                Long personId = share.getPerson().getId();
                BigDecimal part = share.getAmountOwed();

                owed.put(personId, owed.getOrDefault(personId, BigDecimal.ZERO).add(part));
            }
        }

        // 2. Calculer les soldes nets
        Map<Long, BigDecimal> balances = new HashMap<>();
        Set<Long> allIds = new HashSet<>();
        allIds.addAll(paid.keySet());
        allIds.addAll(owed.keySet());

        for (Long personId : allIds) {
            BigDecimal totalPaid = paid.getOrDefault(personId, BigDecimal.ZERO);
            BigDecimal totalOwed = owed.getOrDefault(personId, BigDecimal.ZERO);
            balances.put(personId, totalPaid.subtract(totalOwed));
        }

        // 3. Séparer créditeurs et débiteurs
        List<PersonBalance> creditors = new ArrayList<>();
        List<PersonBalance> debtors = new ArrayList<>();

        for (Map.Entry<Long, BigDecimal> entry : balances.entrySet()) {
            BigDecimal solde = entry.getValue();
            if (solde.compareTo(BigDecimal.ZERO) > 0) {
                creditors.add(new PersonBalance(entry.getKey(), solde));
            } else if (solde.compareTo(BigDecimal.ZERO) < 0) {
                debtors.add(new PersonBalance(entry.getKey(), solde.abs())); // dettes positives
            }
        }

        // 4. Résoudre les dettes
        List<DebtDTO> result = new ArrayList<>();

        for (PersonBalance debtor : debtors) {
            BigDecimal resteARegler = debtor.amount;

            Iterator<PersonBalance> it = creditors.iterator();
            while (resteARegler.compareTo(BigDecimal.ZERO) > 0 && it.hasNext()) {
                PersonBalance creditor = it.next();

                BigDecimal montant = resteARegler.min(creditor.amount);

                result.add(DebtDTO.builder()
                        .from(debtor.personId)
                        .to(creditor.personId)
                        .amount(montant)
                        .build());

                resteARegler = resteARegler.subtract(montant);
                creditor.amount = creditor.amount.subtract(montant);
            }
        }

        return result;
    }

    private static class PersonBalance {
        Long personId;
        BigDecimal amount;

        PersonBalance(Long personId, BigDecimal amount) {
            this.personId = personId;
            this.amount = amount;
        }
    }
}

