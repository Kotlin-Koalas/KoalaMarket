package com.kotlinkoalas.koalamarket.repo.products;

import com.kotlinkoalas.koalamarket.model.products.Technology;
import com.kotlinkoalas.koalamarket.model.products.pk.productPK;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface TechnologyRepository extends JpaRepository<Technology, productPK> {
    Technology findByProductNumber(String productNumber);
    @Transactional
    void deleteByProductNumber(String productNumber);

    boolean existsByProductNumberAndCif(String productNumber, String cif);

    Technology findByProductNumberAndCif(String productNumber, String cif);
}