package com.kotlinkoalas.koalamarket.model.pk;

import java.util.Objects;

public class productPK implements java.io.Serializable{
    private String productNumber;
    private String cif;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final productPK other = (productPK) obj;
        if (!Objects.equals(this.productNumber, other.productNumber)) {
            return false;
        }
        if (!Objects.equals(this.cif, other.cif)) {
            return false;
        }
        return true;
    }
}
