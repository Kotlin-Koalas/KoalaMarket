package com.kotlinkoalas.koalamarket.repo;

import com.kotlinkoalas.koalamarket.model.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.List;

@RepositoryRestController
public interface SearchRepository extends JpaRepository<Search, Long>{
    List<Search> findAllByDni(String dni);
}
