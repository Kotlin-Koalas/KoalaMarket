package com.kotlinkoalas.koalamarket.repo.products;

import com.kotlinkoalas.koalamarket.model.Clothes;
import com.kotlinkoalas.koalamarket.model.pk.productPK;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface ClothesRepository extends JpaRepository<Clothes, productPK> {
    Clothes findByProductNumber(String productNumber);
    @Transactional
    void deleteByProductNumber(String productNumber);

    boolean existsByProductNumberAndCif(String productNumber, String cif);

    Clothes findByProductNumberAndCif(String productNumber, String cif);
}