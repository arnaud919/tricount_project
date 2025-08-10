package com.hb.tricount.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hb.tricount.entity.Payment;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByGroupId(Long groupId);

    List<Payment> findByDebtorId(Long personId);

    List<Payment> findByCreditorId(Long personId);

    @Query("""
            SELECT COALESCE(SUM(p.amount), 0)
            FROM Payment p
            WHERE p.debtor.id = :debtorId AND p.creditor.id = :creditorId AND p.group.id = :groupId
            """)
    BigDecimal getTotalPaidBetween(@Param("debtorId") Long debtorId,
            @Param("creditorId") Long creditorId,
            @Param("groupId") Long groupId);
}
