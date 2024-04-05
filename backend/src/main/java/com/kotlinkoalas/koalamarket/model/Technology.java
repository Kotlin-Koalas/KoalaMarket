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
    private float electricConsumption;

    @Column(name = "brand", nullable = false)
    private String brand;
}
