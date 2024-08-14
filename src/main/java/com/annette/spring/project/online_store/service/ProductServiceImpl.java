package com.annette.spring.project.online_store.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annette.spring.project.online_store.entity.Category;
import com.annette.spring.project.online_store.entity.Product;
import com.annette.spring.project.online_store.repository.CategoryRepository;
import com.annette.spring.project.online_store.repository.ProductRepoCustom;
import com.annette.spring.project.online_store.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductRepoCustom productRepoCustom;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Map<String, Object>> getAllProducts() {
        
        return productRepoCustom.findAllCustom();

    }

    @Override
    public List<Map<String, Object>> getAllProductsByCategory(String category) {
        
        return productRepoCustom.findByCategory(category);

    }

    @Override
    public Map<String, Object> getProduct(int id) {
        
        return productRepoCustom.findByIdCustom(id);

    }

    @Override
    public Product getProductByName(String name) {

        Optional<Product> optional = productRepository.findByName(name);
        Product product = null;

        if (optional.isPresent()) product = optional.get();
        
        return product;

    }

    @Override
    public Product addProduct(Product product) {
        
        return productRepository.save(product);

    }

    @SuppressWarnings("unchecked")
    @Override
    public String addProductCategory(String idData) {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> resultMap = new LinkedHashMap<>();

        try {
            resultMap = objectMapper.readValue(idData, LinkedHashMap.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }

        int productId = Integer.parseInt(resultMap.get("productId"));
        int categoryId = Integer.parseInt(resultMap.get("categoryId"));
        
        Product product = productRepository.findById(productId).get();
        Category category = categoryRepository.findById(categoryId).get();

        List<Category> categories = product.getCategories();
        List<Product> products = category.getProductsInCategory();

        categories.add(category);
        products.add(product);

        product.setCategories(categories);
        category.setProductsInCategory(products);

        productRepository.save(product);
        categoryRepository.save(category);

        return "Товар " + product.getName() + " был добавлен в категорию " + category.getName();

    }

    @SuppressWarnings("unchecked")
    @Override
    public Product updateProduct(String fields, int id) {
        
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> resultMap = new LinkedHashMap<>();

        try {
            resultMap = objectMapper.readValue(fields, LinkedHashMap.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }

        Product product = productRepository.findById(id).get();

        for (Map.Entry<String, String> field : resultMap.entrySet()) {
            switch (field.getKey()) {
                case "sellerId":
                    product.setSellerId(Integer.parseInt(field.getValue()));
                    break;
                case "name":
                    product.setName(field.getValue());
                    break;
                case "price":
                    product.setPrice(Integer.parseInt(field.getValue()));
                    break;
                case "isAllowed":
                    product.setIsAllowed(Boolean.parseBoolean(field.getValue()));
                    break;
                default:
                    System.out.println("Такого поля нет");
                    break;
            }
        }

        return productRepository.save(product);

    }

    @Override
    public void deleteProduct(int id) {
        
        productRepository.deleteById(id);

    }

}
