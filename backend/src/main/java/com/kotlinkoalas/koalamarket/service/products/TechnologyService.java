package com.kotlinkoalas.koalamarket.service.products;

import com.kotlinkoalas.koalamarket.factory.TechnologyFactory;
import com.kotlinkoalas.koalamarket.model.products.Technology;
import com.kotlinkoalas.koalamarket.repo.products.TechnologyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnologyService {

    private final TechnologyFactory technologyFactory = new TechnologyFactory();

    private final TechnologyRepository repository;

    public TechnologyService(TechnologyRepository repository) {
        this.repository = repository;
    }

    public List<Technology> getAllTechnologies() {
        return repository.findAll();
    }

    public Technology getTechnologyByProductNumber(String productNumber, String cif) {
        return repository.findByProductNumberAndCif(productNumber, cif);
    }

    public Technology createTechnology(String productNumber, String name, double price, String description,
                                 String ecology, int stock, String image, String cif, String electricConsumption, String brand) {
        if (repository.existsByProductNumberAndCif(productNumber, cif)) {
            throw new RuntimeException("Product number and cif already exists");
        }
        Technology technology = (Technology) technologyFactory.createProduct(productNumber,name,price,description,ecology,stock,image,cif,electricConsumption,brand);
        return repository.save(technology);
    }

    public Technology updateTechnology(String productNumber, String name, double price, String description,
                                 String ecology, int stock, String image, String electricConsumption, String brand) {
        Technology existingClothes = repository.findByProductNumber(productNumber);
        existingClothes.setName(name);
        existingClothes.setPrice(price);
        existingClothes.setDescription(description);
        existingClothes.setEcology(ecology);
        existingClothes.setStock(stock);
        existingClothes.setImage(image);

        existingClothes.setBrand(brand);
        existingClothes.setElectricConsumption(electricConsumption);

        return repository.save(existingClothes);
    }

    public void deleteTechnology(String productNumber) {
        repository.deleteByProductNumber(productNumber);
    }
}
