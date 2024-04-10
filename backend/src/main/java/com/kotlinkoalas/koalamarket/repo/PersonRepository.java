package com.kotlinkoalas.koalamarket.repo;

import com.kotlinkoalas.koalamarket.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface PersonRepository extends JpaRepository<Person, Long> {}