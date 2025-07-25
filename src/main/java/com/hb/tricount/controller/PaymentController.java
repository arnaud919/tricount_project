package com.hb.tricount.controller;

import com.hb.tricount.business.PaymentService;
import com.hb.tricount.dto.CreatePaymentDTO;
import com.hb.tricount.dto.PaymentDTO;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody CreatePaymentDTO dto) {
        PaymentDTO created = paymentService.recordPayment(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByGroup(@PathVariable Long groupId) {
        return ResponseEntity.ok(paymentService.getPaymentsByGroup(groupId));
    }
}
