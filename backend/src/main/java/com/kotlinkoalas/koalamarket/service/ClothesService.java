package com.kotlinkoalas.koalamarket.service;

import com.kotlinkoalas.koalamarket.model.Clothes;
import com.kotlinkoalas.koalamarket.repo.ClothesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClothesService {

    private final ClothesRepository repository;

    public ClothesService(ClothesRepository repository) {
        this.repository = repository;
    }

    public List<Clothes> getAllClothes() {
        return repository.findAll();
    }

    public Clothes createClothes(String productNumber, String name, double price, String description,
                                 String ecology, int stock, String image, String cif, String color, String size) {
        if (repository.existsByProductNumberAndCif(productNumber, cif)) {
            throw new RuntimeException("Product number and cif already exists");
        }
        Clothes clothes = new Clothes(productNumber, name, price, description, ecology, stock, image, cif, color, size);
        return repository.save(clothes);
    }

    public Clothes updateClothes(String productNumber, String name, double price, String description,
                                 String ecology, int stock, String image) {
        Clothes existingClothes = repository.findByProductNumber(productNumber);
        existingClothes.setName(name);
        existingClothes.setPrice(price);
        existingClothes.setDescription(description);
        existingClothes.setEcology(ecology);
        existingClothes.setStock(stock);
        existingClothes.setImage(image);
        return repository.save(existingClothes);
    }

    public void deleteClothes(String productNumber) {
        repository.deleteByProductNumber(productNumber);
    }
}
