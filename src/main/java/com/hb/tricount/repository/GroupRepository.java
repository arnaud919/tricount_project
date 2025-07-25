package com.hb.tricount.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hb.tricount.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByMembers_Id(Long personId);
}
