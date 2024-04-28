package com.kotlinkoalas.koalamarket.repo.products;

import com.kotlinkoalas.koalamarket.model.products.Product;
import com.kotlinkoalas.koalamarket.model.products.pk.productPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.List;

@RepositoryRestController
public interface ProductRepository extends JpaRepository<Product, productPK> {
    boolean existsByProductNumber(String productNumber);

    @Query("SELECT DISTINCT p.productNumber FROM Product p")
    List<String> findDistinctProducts();

    List<Product> findByProductNumber(String productNumber);

    List<Product> findByCif(String cif);
}