package com.kotlinkoalas.koalamarket.factory;

import com.kotlinkoalas.koalamarket.model.Product;

public abstract class ProductFactory {
    abstract Product createProduct(String productNumber, String name, double price, String description, String ecology, int stock, String image, String... additionalParams);
}