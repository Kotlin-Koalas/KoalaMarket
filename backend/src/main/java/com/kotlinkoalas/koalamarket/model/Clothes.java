package com.kotlinkoalas.koalamarket.model;


import com.kotlinkoalas.koalamarket.model.pk.productPK;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@IdClass(value = productPK.class)
public class Clothes extends Product{
    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "size", nullable = false)
    private String size;

    public Clothes(String productNumber, String name, double price, String description, String ecology, int stock, String image, String cif, String color, String size){
        super("clothes",productNumber, name, price, description, ecology, stock, image, cif);
        this.color = color;
        this.size = size;
    }

}