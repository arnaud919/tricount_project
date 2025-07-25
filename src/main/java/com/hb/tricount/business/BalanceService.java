package com.hb.tricount.business;

import com.hb.tricount.dto.DebtDTO;

import java.util.List;

public interface BalanceService {
    List<DebtDTO> calculateGroupBalance(Long groupId);
}
