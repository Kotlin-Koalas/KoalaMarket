package com.kotlinkoalas.koalamarket.repo;

import com.kotlinkoalas.koalamarket.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.List;

@RepositoryRestController
public interface WishListRepository extends JpaRepository<WishList, String> {
    List<WishList> findAllByBuyerId(String buyerId);
    WishList findByBuyerIdAndProductNumberAndCif(String buyerId, String productNumber, String cif);
}
