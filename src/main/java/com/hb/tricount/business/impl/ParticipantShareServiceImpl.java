package com.hb.tricount.business.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hb.tricount.business.ParticipantShareService;
import com.hb.tricount.dto.DebtDTO;
import com.hb.tricount.dto.ParticipantShareDTO;
import com.hb.tricount.entity.ParticipantShare;
import com.hb.tricount.entity.Person;
import com.hb.tricount.repository.ParticipantShareRepository;
import com.hb.tricount.repository.PaymentRepository;
import com.hb.tricount.repository.PersonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParticipantShareServiceImpl implements ParticipantShareService {

    private final ParticipantShareRepository participantShareRepository;
    private final PaymentRepository paymentRepository;
    private final PersonRepository personRepository;

    @Override
    public List<ParticipantShareDTO> getSharesByGroupId(Long groupId) {
        List<ParticipantShare> shares = participantShareRepository.findByGroupId(groupId);

        return shares.stream().map(s -> ParticipantShareDTO.builder()
                .personId(s.getPerson().getId())
                .personName(s.getPerson().getName())
                .expenseId(s.getExpense().getId())
                .description(s.getExpense().getDescription())
                .amountOwed(s.getAmountOwed())
                .build()).toList();
    }

    @Override
    public List<DebtDTO> calculateGroupBalance(Long groupId) {
        // Étape 1 : récupérer les parts de dépenses dans le groupe
        List<ParticipantShare> shares = participantShareRepository.findAllByGroupId(groupId);

        // Étape 2 : regrouper les dettes théoriques entre débiteurs et créditeurs
        Map<String, BigDecimal> theoreticalDebts = new HashMap<>();

        for (ParticipantShare share : shares) {
            Person debtor = share.getPerson();
            Person creditor = share.getExpense().getPayer();
            BigDecimal amount = share.getAmountOwed();

            if (!debtor.equals(creditor)) {
                String key = debtor.getId() + "-" + creditor.getId();
                theoreticalDebts.put(key, theoreticalDebts.getOrDefault(key, BigDecimal.ZERO).add(amount));
            }
        }

        // Étape 3 : préparation du cache des noms (évite les appels répétés à findById)
        Map<Long, String> personNameCache = new HashMap<>();

        Function<Long, String> getName = id -> personNameCache.computeIfAbsent(
                id,
                i -> personRepository.findById(i)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found: " + i))
                        .getName());

        // Étape 4 : générer les dettes finales après prise en compte des paiements
        List<DebtDTO> remainingDebts = new ArrayList<>();

        for (Map.Entry<String, BigDecimal> entry : theoreticalDebts.entrySet()) {
            String[] parts = entry.getKey().split("-");
            Long debtorId = Long.parseLong(parts[0]);
            Long creditorId = Long.parseLong(parts[1]);
            BigDecimal theoreticalAmount = entry.getValue();

            BigDecimal alreadyPaid = paymentRepository.getTotalPaidBetween(debtorId, creditorId, groupId);
            BigDecimal remaining = theoreticalAmount.subtract(alreadyPaid);

            if (remaining.compareTo(BigDecimal.ZERO) > 0) {
                DebtDTO dto = DebtDTO.builder()
                        .debtorId(debtorId)
                        .debtorName(getName.apply(debtorId))
                        .creditorId(creditorId)
                        .creditorName(getName.apply(creditorId))
                        .amount(remaining)
                        .build();

                remainingDebts.add(dto);
            }
        }

        return remainingDebts;
    }

    private String generateKey(Long debtorId, Long creditorId) {
        return debtorId + "-" + creditorId;
    }
}
