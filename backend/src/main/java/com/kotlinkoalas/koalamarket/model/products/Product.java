package com.kotlinkoalas.koalamarket.model.products;

import com.kotlinkoalas.koalamarket.model.products.pk.productPK;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@IdClass(value = productPK.class)
public abstract class Product {
    @Id
    @Column(name = "product_number", length = 30, nullable = false)
    private String productNumber;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "ecology", nullable = false)
    private String ecology;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "category", nullable = false)
    private String category;

    @Id
    @Column(name = "cif", length = 30, nullable = false)
    private String cif;

    public Product(String category,String productNumber, String name, double price, String description, String ecology, int stock, String image,String cif) {
        this.category = category;
        this.productNumber = productNumber;
        this.name = name;
        this.price = price;
        this.description = description;
        this.ecology = ecology;
        this.stock = stock;
        this.image = image;
        this.cif = cif;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Product product = (Product) o;
        return getProductNumber() != null && Objects.equals(getProductNumber(), product.getProductNumber())
                && getCif() != null && Objects.equals(getCif(), product.getCif());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(productNumber, cif);
    }
}
