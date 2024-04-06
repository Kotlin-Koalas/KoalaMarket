package com.kotlinkoalas.koalamarket.repo;

import com.kotlinkoalas.koalamarket.model.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface ClothesRepository extends JpaRepository<Clothes, Long> {}
