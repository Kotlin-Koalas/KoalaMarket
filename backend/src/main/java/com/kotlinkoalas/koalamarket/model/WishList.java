package com.kotlinkoalas.koalamarket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String buyerId;

    private String productNumber;

    private String cif;

    private String category;

    public WishList(String productNumber, String cif, String category, String buyerId) {
        this.productNumber = productNumber;
        this.cif = cif;
        this.category = category;
        this.buyerId = buyerId;
    }
}
