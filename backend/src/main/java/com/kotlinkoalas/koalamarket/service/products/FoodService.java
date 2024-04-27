package com.kotlinkoalas.koalamarket.service.products;

import com.kotlinkoalas.koalamarket.factory.FoodFactory;
import com.kotlinkoalas.koalamarket.model.products.Food;
import com.kotlinkoalas.koalamarket.repo.products.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    private final FoodRepository repository;

    private final FoodFactory foodFactory = new FoodFactory();

    public FoodService(FoodRepository repository) {
        this.repository = repository;
    }

    public List<Food> getAllFoods() {
        return repository.findAll();
    }

    public Food getFoodByProductNumber(String productNumber, String cif) {
        return repository.findByProductNumberAndCif(productNumber, cif);
    }

    public Food createFood(String productNumber, String name, double price, String description,
                                 String ecology, int stock, String image, String cif, String calories, String macros) {
        if (repository.existsByProductNumberAndCif(productNumber, cif)) {
            throw new RuntimeException("Product number and cif already exists");
        }
        Food food = (Food) foodFactory.createProduct(productNumber,name,price,description,ecology,stock,image,cif,calories,macros);
        return repository.save(food);
    }

    public Food updateFood(String productNumber, String name, double price, String description,
                                 String ecology, int stock, String image, String macros, int calories) {
        Food existingClothes = repository.findByProductNumber(productNumber);
        existingClothes.setName(name);
        existingClothes.setPrice(price);
        existingClothes.setDescription(description);
        existingClothes.setEcology(ecology);
        existingClothes.setStock(stock);
        existingClothes.setImage(image);
        existingClothes.setCalories(calories);
        existingClothes.setMacros(macros);

        return repository.save(existingClothes);
    }

    public void deleteFood(String productNumber) {
        repository.deleteByProductNumber(productNumber);
    }
}
