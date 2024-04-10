package com.kotlinkoalas.koalamarket.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "vendor")
public class Vendor {
    @Column(name = "bank_data", nullable = false)
    private String bankData;

    @Column(name = "cif", length = 30, nullable = false, unique = true)
    private String cif;

    @Id
    @Column(name = "dni", length = 30, nullable = false)
    private String dni;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dni", referencedColumnName = "dni")
    private Client user;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Vendor vendor = (Vendor) o;
        return getDni() != null && Objects.equals(getDni(), vendor.getDni());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
