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
@Entity
@RequiredArgsConstructor
@IdClass(value = productPK.class)
public class Food extends Product{

    @Column(name = "calories", nullable = true)
    private int calories;

    @Column(name = "macros", nullable = true)
    private String macros;

    public Food(String productNumber, String name, double price, String description, String ecology, int stock, String image, String cif, int calories, String macros) {
        super("food",productNumber, name, price, description, ecology, stock, image, cif);
        this.calories = calories;
        this.macros = macros;
    }

}

