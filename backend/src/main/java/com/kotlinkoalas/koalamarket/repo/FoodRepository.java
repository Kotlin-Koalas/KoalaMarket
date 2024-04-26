package com.kotlinkoalas.koalamarket.repo;

import com.kotlinkoalas.koalamarket.model.Food;
import com.kotlinkoalas.koalamarket.model.pk.productPK;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface FoodRepository extends JpaRepository<Food, productPK> {
    Food findByProductNumber(String productNumber);
    @Transactional
    void deleteByProductNumber(String productNumber);

    boolean existsByProductNumberAndCif(String productNumber, String cif);

    Food findByProductNumberAndCif(String productNumber, String cif);
}