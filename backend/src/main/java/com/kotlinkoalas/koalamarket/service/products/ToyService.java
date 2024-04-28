package com.kotlinkoalas.koalamarket.service.products;

import com.kotlinkoalas.koalamarket.factory.ToyFactory;
import com.kotlinkoalas.koalamarket.model.products.Toy;
import com.kotlinkoalas.koalamarket.repo.products.ToyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToyService {

    private final ToyFactory toyFactory = new ToyFactory();

    private final ToyRepository repository;

    public ToyService(ToyRepository repository) {
        this.repository = repository;
    }

    public List<Toy> getAllToys() {
        return repository.findAll();
    }

    public Toy getToyByProductNumber(String productNumber, String cif) {
        return repository.findByProductNumberAndCif(productNumber, cif);
    }

    public Toy createToy(String productNumber, String name, double price, String description,
                                 String ecology, int stock, String image, String cif, String material, String age) {
        if (repository.existsByProductNumberAndCif(productNumber, cif)) {
            throw new RuntimeException("Product number and cif already exists");
        }
        Toy technology = (Toy) toyFactory.createProduct(productNumber,name,price,description,ecology,stock,image,cif,material,age);
        return repository.save(technology);
    }

    public Toy updateToy(String productNumber, String name, double price, String description,
                                 String ecology, int stock, String image, String material, String age) {
        Toy existingClothes = repository.findByProductNumber(productNumber);
        existingClothes.setName(name);
        existingClothes.setPrice(price);
        existingClothes.setDescription(description);
        existingClothes.setEcology(ecology);
        existingClothes.setStock(stock);
        existingClothes.setImage(image);

        existingClothes.setMaterial(material);
        existingClothes.setAge(age);

        return repository.save(existingClothes);
    }

    public void deleteToy(String productNumber) {
        repository.deleteByProductNumber(productNumber);
    }
}
