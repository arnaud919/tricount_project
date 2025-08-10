package com.hb.tricount.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hb.tricount.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByEmail(String email);
}
