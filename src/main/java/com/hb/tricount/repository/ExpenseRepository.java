package com.hb.tricount.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hb.tricount.entity.Expense;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByGroupId(Long groupId);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.group.id = :groupId")
    BigDecimal getTotalAmountByGroupId(@Param("groupId") Long groupId);

    @Query("""
                SELECT e FROM Expense e
                WHERE e.group.id = :groupId
                AND (
                    (:type = 'above' AND e.amount >= :amount)
                    OR (:type = 'below' AND e.amount <= :amount)
                )
            """)
    List<Expense> findByGroupAndAmountFilter(
            @Param("groupId") Long groupId,
            @Param("amount") BigDecimal amount,
            @Param("type") String type);

    List<Expense> findByPayer_Id(Long personId);
}
