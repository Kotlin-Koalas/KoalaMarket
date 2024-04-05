package com.kotlinkoalas.koalamarket.factory;

import com.kotlinkoalas.koalamarket.model.Clothes;
import com.kotlinkoalas.koalamarket.model.Product;

public class ClothesFactory extends ProductFactory {

    @Override
    public Product createProduct(String productNumber, String name, double price, String description, String ecology, int stock, String image, String... additionalParams) {
        return new Clothes(productNumber, name, price, description, ecology, stock, image, additionalParams[0], additionalParams[1]);
    }
}