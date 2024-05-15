package com.kotlinkoalas.koalamarket.repo.products;

import com.kotlinkoalas.koalamarket.model.products.SatisfactionUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SatisfactionRepository extends JpaRepository<SatisfactionUser,Long> {
    SatisfactionUser findByProductNumberAndDni(String productNumber, String dni);
    List<SatisfactionUser> findAllByProductNumber(String productNumber);
}
