package com.kotlinkoalas.koalamarket.factory;

import com.kotlinkoalas.koalamarket.model.Product;
import com.kotlinkoalas.koalamarket.model.Technology;
import com.kotlinkoalas.koalamarket.model.Toy;

public class ToyFactory extends ProductFactory{
    @Override
    public Product createProduct(String productNumber, String name, double price, String description, String ecology, int stock, String image, String cif, String... additionalParams) {
        return new Toy(productNumber, name, price, description, ecology, stock, image, cif, additionalParams[0], additionalParams[1]);
    }
}
