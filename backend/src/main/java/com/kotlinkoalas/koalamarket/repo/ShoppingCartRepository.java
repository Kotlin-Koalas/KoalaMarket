package com.kotlinkoalas.koalamarket.repo;

import com.kotlinkoalas.koalamarket.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>{
    //public ShoppingCart findByClientId(String clientId);
    @Query("SELECT sc FROM ShoppingCart sc WHERE sc.buyer.id = :clientId")
    ShoppingCart findByClientId(@Param("clientId") String clientId);
}
