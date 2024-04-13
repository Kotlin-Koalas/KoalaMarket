package com.kotlinkoalas.koalamarket.repo;

import com.kotlinkoalas.koalamarket.model.Technology;
import com.kotlinkoalas.koalamarket.model.pk.productPK;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface TechnologyRepository extends JpaRepository<Technology, productPK> {
    Technology findByProductNumber(String productNumber);
    @Transactional
    void deleteByProductNumber(String productNumber);

    boolean existsByProductNumberAndCif(String productNumber, String cif);
}