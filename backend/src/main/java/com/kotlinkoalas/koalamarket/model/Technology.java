package com.kotlinkoalas.koalamarket.model;

import com.kotlinkoalas.koalamarket.model.pk.productPK;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@IdClass(productPK.class)
public class Technology extends Product {

    @Column(name = "electric_Consumption")
    private String electricConsumption;

    @Column(name = "brand", nullable = false)
    private String brand;

    public Technology(String productNumber, String name, double price, String description, String ecology, int stock, String image,String cif, String electricConsumption, String brand){
        super(productNumber, name, price, description, ecology, stock, image, cif);
        this.electricConsumption = electricConsumption;
        this.brand = brand;
    }

}
