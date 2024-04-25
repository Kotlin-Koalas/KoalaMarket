package com.kotlinkoalas.koalamarket.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String buyerId;

    private String productNumber;

    private String cif;

    private String category;

    private int quantity;

    public CartItem(String productNumber, String cif, String category, int quantity, String buyerId) {
        this.productNumber = productNumber;
        this.cif = cif;
        this.category = category;
        this.quantity = quantity;
        this.buyerId = buyerId;
    }
}
