package com.kotlinkoalas.koalamarket.repo;

import com.kotlinkoalas.koalamarket.model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerRepository extends JpaRepository<Buyer, String> {
    Buyer findByEmail(String email);
    Buyer findByDni(String dni);
}