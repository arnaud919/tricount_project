package com.hb.tricount.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hb.tricount.entity.ParticipantShare;

import java.util.List;

public interface ParticipantShareRepository extends JpaRepository<ParticipantShare, Long> {

    List<ParticipantShare> findByPersonId(Long personId);
    List<ParticipantShare> findByExpenseId(Long expenseId);
    List<ParticipantShare> findByGroupId(Long groupId);
    @Query("""
    SELECT ps FROM ParticipantShare ps
    WHERE ps.expense.group.id = :groupId
    """)
List<ParticipantShare> findAllByGroupId(@Param("groupId") Long groupId);

}
