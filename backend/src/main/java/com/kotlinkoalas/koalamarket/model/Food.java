package com.kotlinkoalas.koalamarket.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
public class Food extends Product{
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

