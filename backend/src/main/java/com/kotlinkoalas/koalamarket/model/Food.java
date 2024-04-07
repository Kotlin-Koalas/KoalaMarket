package com.kotlinkoalas.koalamarket.model;

import com.kotlinkoalas.koalamarket.model.pk.productPK;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@IdClass(value = productPK.class)
public class Food extends Product{
    @Id
    @Column(name = "cif")
    private String cif;

    @Id
    @Column(name = "product_number")
    private String productNumber;

    @Column(name = "calories", nullable = false)
    private int calories;

    @Column(name = "macros", nullable = false)
    private String macros;
    public Food(String productNumber, String name, double price, String description, String ecology, int stock, String image, int calories, String macros) {
        super(productNumber, name, price, description, ecology, stock, image);
        this.calories = calories;
        this.macros = macros;
    }

}

