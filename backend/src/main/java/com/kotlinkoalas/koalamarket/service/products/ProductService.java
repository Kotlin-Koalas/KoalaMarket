package com.kotlinkoalas.koalamarket.service.products;

import com.kotlinkoalas.koalamarket.model.products.*;
import com.kotlinkoalas.koalamarket.repo.ClientRepository;
import com.kotlinkoalas.koalamarket.repo.products.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private final ProductRepository repository;

    private final ClientRepository clientRepository;

    private final ClothesService clothesService;

    private final FoodService foodService;

    private final TechnologyService technologyService;

    private final ToyService toyService;

    public ProductService(ProductRepository repository, ClientRepository clientRepository, ClothesService clothesService, FoodService foodService, TechnologyService technologyService, ToyService toyService) {
        this.repository = repository;
        this.clientRepository = clientRepository;
        this.clothesService = clothesService;
        this.foodService = foodService;
        this.technologyService = technologyService;
        this.toyService = toyService;
    }

    public ResponseEntity<Map<String,Object>> getProduct(String productNumber) {
        List<Product> items= repository.findByProductNumber(productNumber);
        if(items.isEmpty()){
            return ResponseEntity.ok(Map.of("message", "Product not found"));
        }
        Map<String,Object> response = new HashMap<>();

        List<Map<String , Object>> itemsList = new ArrayList<>();

        for(Product item : items){
            String itemProductNumber = item.getProductNumber();
            String itemCif = item.getCif();
            String itemCategory = item.getCategory();
            Map<String, Object> itemDetails = new HashMap<>();
            itemDetails.put("productNumber", itemProductNumber);
            itemDetails.put("category", itemCategory);
            itemDetails.put("cif", itemCif);
            switch (itemCategory) {
                case "clothes":
                    Clothes clothes = clothesService.getClothesByProductNumber(itemProductNumber, itemCif);
                    itemDetails.put("name", clothes.getName());
                    itemDetails.put("price", clothes.getPrice());
                    itemDetails.put("description", clothes.getDescription());
                    itemDetails.put("ecology", clothes.getEcology());
                    itemDetails.put("stock", clothes.getStock());
                    itemDetails.put("image", clothes.getImage());
                    itemDetails.put("color", clothes.getColor());
                    itemDetails.put("size", clothes.getSize());
                    break;
                case "food":
                    Food food = foodService.getFoodByProductNumber(itemProductNumber, itemCif);
                    itemDetails.put("name", food.getName());
                    itemDetails.put("price", food.getPrice());
                    itemDetails.put("description", food.getDescription());
                    itemDetails.put("ecology", food.getEcology());
                    itemDetails.put("stock", food.getStock());
                    itemDetails.put("image", food.getImage());
                    itemDetails.put("calories", food.getCalories());
                    itemDetails.put("macros", food.getMacros());
                    break;
                case "technology":
                    Technology technology = technologyService.getTechnologyByProductNumber(itemProductNumber, itemCif);
                    itemDetails.put("name", technology.getName());
                    itemDetails.put("price", technology.getPrice());
                    itemDetails.put("description", technology.getDescription());
                    itemDetails.put("ecology", technology.getEcology());
                    itemDetails.put("stock", technology.getStock());
                    itemDetails.put("image", technology.getImage());
                    itemDetails.put("brand", technology.getBrand());
                    itemDetails.put("electricConsumption", technology.getElectricConsumption());
                    break;
                case "toy":
                    Toy toy = toyService.getToyByProductNumber(itemProductNumber, itemCif);
                    itemDetails.put("name", toy.getName());
                    itemDetails.put("price", toy.getPrice());
                    itemDetails.put("description", toy.getDescription());
                    itemDetails.put("ecology", toy.getEcology());
                    itemDetails.put("stock", toy.getStock());
                    itemDetails.put("image", toy.getImage());
                    itemDetails.put("age", toy.getAge());
                    itemDetails.put("material", toy.getMaterial());
                    break;
            }

            itemDetails.put("vendorName",clientRepository.findByDni(itemCif).getName());
            itemsList.add(itemDetails);
        }
        response.put("items",itemsList);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> existsProduct(String productNumber){
        return ResponseEntity.ok(repository.existsByProductNumber(productNumber));
    }

    public List<Product> allDistinctProducts(){
        List<String> productNumbers = repository.findDistinctProducts();
        List<Product> products = new ArrayList<>();
        for (String productNumber : productNumbers) {
            products.add(repository.findByProductNumber(productNumber).get(0));
        }
        return products;
    }

    public ResponseEntity<String> updateProduct(String productNumber, String cif, String price, int stock){
        Product product = repository.findByCifAndProductNumber(productNumber, cif);
        if(product == null){
            return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body("Item not found");
        }
        product.setPrice(Double.parseDouble(price));
        product.setStock(stock);
        repository.save(product);
        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body("Item updated successfully");
    }

    public ResponseEntity<String> deleteProduct(String productNumber, String cif){
        Product product = repository.findByCifAndProductNumber(productNumber, cif);
        if(product == null){
            return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body("Item not found");
        }
        repository.delete(product);
        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body("Item deleted successfully");
    }

    public List<Product> allVendorProducts(String cif){
        return repository.findByCif(cif);
    }
}

