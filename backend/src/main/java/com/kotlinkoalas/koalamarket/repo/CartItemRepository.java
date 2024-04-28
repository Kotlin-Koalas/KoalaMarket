package com.kotlinkoalas.koalamarket.repo;

import com.kotlinkoalas.koalamarket.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.List;

@RepositoryRestController
public interface CartItemRepository extends JpaRepository<CartItem, Long>{

    List<CartItem> findAllByBuyerId(String buyerId);
}
