package com.hb.tricount.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hb.tricount.entity.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByGroupId(Long groupId);
    List<Payment> findByDebtorId(Long personId);
    List<Payment> findByCreditorId(Long personId);
}
