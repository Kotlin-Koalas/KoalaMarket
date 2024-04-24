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

    @ManyToOne
    private Product product;

    private int quantity;

    @ManyToOne
    private ShoppingCart shoppingCart;

    public CartItem(Product product, int quantity, ShoppingCart shoppingCart) {
        this.product = product;
        this.quantity = quantity;
        this.shoppingCart = shoppingCart;
    }
}
