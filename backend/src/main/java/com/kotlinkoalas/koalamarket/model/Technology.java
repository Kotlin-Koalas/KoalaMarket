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
@IdClass(productPK.class)
public class Technology extends Product {
    @Id
    @Column(name = "cif")
    private String cif;

    @Id
    @Column(name = "product_number")
    private String productNumber;

    @Column(name = "electric_Consumption", nullable = false)
    private double electricConsumption;

    @Column(name = "brand", nullable = false)
    private String brand;

    public Technology(String productNumber, String name, double price, String description, String ecology, int stock, String image, double electricConsumption, String brand){
        super(productNumber, name, price, description, ecology, stock, image);
        this.electricConsumption = electricConsumption;
        this.brand = brand;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Technology that = (Technology) o;
        return getCif() != null && Objects.equals(getCif(), that.getCif())
                && getProductNumber() != null && Objects.equals(getProductNumber(), that.getProductNumber());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getCif(),
                getProductNumber(),
                getProductNumber(),
                getCif());
    }
}
