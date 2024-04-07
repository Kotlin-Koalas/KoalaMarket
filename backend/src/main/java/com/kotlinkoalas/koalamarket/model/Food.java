package com.kotlinkoalas.koalamarket.model;

import com.kotlinkoalas.koalamarket.model.pk.productPK;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;


@Entity
@RequiredArgsConstructor
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

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Food food = (Food) o;
        return getCif() != null && Objects.equals(getCif(), food.getCif())
                && getProductNumber() != null && Objects.equals(getProductNumber(), food.getProductNumber());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getCif(),
                getProductNumber(),
                getProductNumber(),
                getCif());
    }
}

