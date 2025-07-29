package com.hb.tricount.business.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hb.tricount.business.ParticipantShareService;
import com.hb.tricount.dto.ParticipantShareDTO;
import com.hb.tricount.entity.ParticipantShare;
import com.hb.tricount.repository.ParticipantShareRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParticipantShareServiceImpl implements ParticipantShareService {

    private final ParticipantShareRepository participantShareRepository;

    @Override
    public List<ParticipantShareDTO> getSharesByGroupId(Long groupId) {
        List<ParticipantShare> shares = participantShareRepository.findByGroupId(groupId);

        return shares.stream().map(s -> ParticipantShareDTO.builder()
                .personId(s.getPerson().getId())
                .personName(s.getPerson().getName())
                .expenseId(s.getExpense().getId())
                .description(s.getExpense().getDescription())
                .amountOwed(s.getAmountOwed())
                .build()
        ).toList();
    }

}
