package com.hb.tricount.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hb.tricount.entity.ParticipantShare;

import java.util.List;

public interface ParticipantShareRepository extends JpaRepository<ParticipantShare, Long> {

    List<ParticipantShare> findByPersonId(Long personId);
    List<ParticipantShare> findByExpenseId(Long expenseId);
    List<ParticipantShare> findByGroupId(Long groupId);

}
