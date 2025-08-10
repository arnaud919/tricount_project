package com.hb.tricount.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hb.tricount.entity.Group;
import com.hb.tricount.entity.Person;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByMembers_Id(Long personId);
    List<Group> findByMembersContaining(Person person);

    @Query("""
    SELECT DISTINCT g FROM Group g
    LEFT JOIN FETCH g.creator
    LEFT JOIN FETCH g.members
    WHERE :person MEMBER OF g.members
    """)
List<Group> findAllByPersonWithRelations(@Param("person") Person person);

}
