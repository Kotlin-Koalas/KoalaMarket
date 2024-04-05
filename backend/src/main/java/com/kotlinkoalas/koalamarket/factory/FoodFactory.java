package com.kotlinkoalas.koalamarket.factory;

import com.kotlinkoalas.koalamarket.model.Food;
import com.kotlinkoalas.koalamarket.model.Product;

public class FoodFactory implements ProductFactory {
    public Food createProduct(String productNumber, String name, double price, String description, String ecology, int stock, String image, String... additionalDetails) {
        String macros = additionalDetails[1];
        int calories = Integer.parseInt(additionalDetails[0]);
        return new Food(productNumber, name, price, description, ecology, stock, image, calories, macros);
    }
}