package com.kotlinkoalas.koalamarket.factory;

import com.kotlinkoalas.koalamarket.model.products.Product;

public abstract class ProductFactory {
    public abstract Product createProduct(String productNumber, String name, double price, String description, String ecology, int stock, String image, String cif , String... additionalParams);
}