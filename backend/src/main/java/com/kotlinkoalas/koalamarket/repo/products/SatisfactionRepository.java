package com.kotlinkoalas.koalamarket.repo.products;

import com.kotlinkoalas.koalamarket.model.products.SatisfactionUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.List;

@RepositoryRestController
public interface SatisfactionRepository extends JpaRepository<SatisfactionUser,Long> {
    SatisfactionUser findByProductNumberAndDni(String productNumber, String dni);
    List<SatisfactionUser> findAllByProductNumber(String productNumber);
}
