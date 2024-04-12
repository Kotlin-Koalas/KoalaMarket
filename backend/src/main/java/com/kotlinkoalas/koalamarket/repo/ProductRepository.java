package com.kotlinkoalas.koalamarket.repo;

import com.kotlinkoalas.koalamarket.model.Product;
import com.kotlinkoalas.koalamarket.model.Technology;
import com.kotlinkoalas.koalamarket.model.pk.productPK;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface ProductRepository extends JpaRepository<Product, productPK> {
    boolean existsByProductNumber(String productNumber);
}