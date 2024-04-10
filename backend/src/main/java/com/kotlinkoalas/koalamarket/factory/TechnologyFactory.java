package com.kotlinkoalas.koalamarket.factory;

import com.kotlinkoalas.koalamarket.model.Product;
import com.kotlinkoalas.koalamarket.model.Technology;

public class TechnologyFactory extends ProductFactory {

    @Override
    public Product createProduct(String productNumber, String name, double price, String description, String ecology, int stock, String image,String cif, String... additionalParams) {
        return new Technology(productNumber, name, price, description, ecology, stock, image, cif, Double.parseDouble(additionalParams[0]), additionalParams[1]);
    }
}