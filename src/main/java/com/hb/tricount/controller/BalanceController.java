package com.hb.tricount.controller;

import com.hb.tricount.business.BalanceService;
import com.hb.tricount.dto.DebtDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/balance")
public class BalanceController {

    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<DebtDTO>> getGroupBalance(@PathVariable Long groupId) {
        return ResponseEntity.ok(balanceService.calculateGroupBalance(groupId));
    }
}
