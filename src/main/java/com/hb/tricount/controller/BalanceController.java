package com.hb.tricount.controller;

import com.hb.tricount.business.ParticipantShareService;
import com.hb.tricount.dto.DebtDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/balance")
@RequiredArgsConstructor
public class BalanceController {

    private final ParticipantShareService participantShareService;

    @GetMapping("/{groupId}")
    public ResponseEntity<List<DebtDTO>> getGroupBalance(@PathVariable Long groupId) {
        List<DebtDTO> debts = participantShareService.calculateGroupBalance(groupId);
        return ResponseEntity.ok(debts);
    }
}
