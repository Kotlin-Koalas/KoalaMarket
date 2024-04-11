package com.kotlinkoalas.koalamarket.factory;

import com.kotlinkoalas.koalamarket.model.Food;
import com.kotlinkoalas.koalamarket.model.Product;

public class FoodFactory extends ProductFactory {

    @Override
    public Product createProduct(String productNumber, String name, double price, String description, String ecology, int stock, String image, String cif, String... additionalParams) {
        return new Food(productNumber, name, price, description, ecology, stock, image, cif, Integer.parseInt(additionalParams[0]), additionalParams[1]);
    }
}