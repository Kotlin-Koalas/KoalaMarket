package com.kotlinkoalas.koalamarket.repo.products;

import com.kotlinkoalas.koalamarket.model.Toy;
import com.kotlinkoalas.koalamarket.model.pk.productPK;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface ToyRepository extends JpaRepository<Toy, productPK> {
    Toy findByProductNumber(String productNumber);
    @Transactional
    void deleteByProductNumber(String productNumber);

    boolean existsByProductNumberAndCif(String productNumber, String cif);

    Toy findByProductNumberAndCif(String productNumber, String cif);
}