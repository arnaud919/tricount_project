package com.hb.tricount.business;

import java.util.List;

import com.hb.tricount.dto.CreatePaymentDTO;
import com.hb.tricount.dto.PaymentDTO;

public interface PaymentService {
    PaymentDTO recordPayment(CreatePaymentDTO dto);
    List<PaymentDTO> getPaymentsByGroup(Long groupId);

}
