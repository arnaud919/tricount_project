package com.hb.tricount.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hb.tricount.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

    // Exemples de requêtes personnalisées
    Optional<Person> findByEmail(String email);
}
