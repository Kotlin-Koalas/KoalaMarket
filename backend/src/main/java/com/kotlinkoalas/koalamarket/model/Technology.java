package com.kotlinkoalas.koalamarket.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Technology extends Product {
    @Column(name = "electric_Consumption", nullable = false)
    private double electricConsumption;

    @Column(name = "brand", nullable = false)
    private String brand;

    public Technology(String productNumber, String name, double price, String description, String ecology, int stock, String image, double electricConsumption, String brand){
        super(productNumber, name, price, description, ecology, stock, image);
        this.electricConsumption = electricConsumption;
        this.brand = brand;
    }
}
