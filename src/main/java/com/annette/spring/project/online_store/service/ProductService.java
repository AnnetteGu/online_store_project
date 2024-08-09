package com.annette.spring.project.online_store.service;

import java.util.List;

import com.annette.spring.project.online_store.entity.Product;

public interface ProductService {

    public List<Product> getAllProducts();

    public List<Product> getAllProductsByCategory(String category);

    public Product getProduct(int id);

    public Product getProductByName(String name);

    public Product addProduct(Product product);

    public String addProductCategory(String idData);

    public Product updateProduct(String fields, int id);

    public void deleteProduct(int id);

}
