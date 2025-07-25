package com.hb.tricount.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.hb.tricount.entity.Expense;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByGroupId(Long groupId);
}
