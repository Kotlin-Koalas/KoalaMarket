package com.kotlinkoalas.koalamarket.model.products;

import com.kotlinkoalas.koalamarket.model.products.pk.productPK;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@IdClass(productPK.class)
public class Toy extends Product{

    @Column(name = "material", nullable = false)
    private String material;

    @Column(name = "age", nullable = false)
    private String age;

    public Toy(String productNumber, String name, double price, String description, String ecology, int stock, String image,String cif, String material, String age){
        super("toy",productNumber, name, price, description, ecology, stock, image, cif);
        this.material = material;
        this.age = age;
    }
}
