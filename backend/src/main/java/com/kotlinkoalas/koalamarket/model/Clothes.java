package com.kotlinkoalas.koalamarket.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.lang.annotation.Inherited;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
public class Clothes extends Product{
    @Id
    @Column(name = "cif")
    private String cif;

    @Id
    @Column(name = "product_number")
    private String productNumber;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "size", nullable = false)
    private String size;

    public Clothes(String productNumber, String name, double price, String description, String ecology, int stock, String image, String color, String size){
        super(productNumber, name, price, description, ecology, stock, image);
        this.color = color;
        this.size = size;
    }
}
