package com.annette.spring.project.online_store.service;

import java.util.List;

import com.annette.spring.project.online_store.entity.Basket;
import com.annette.spring.project.online_store.entity.Product;

public interface BasketService {

    public List<Product> getAllBasketProducts(int basketId);

    public Product getBasketProduct(int productId);

    public Product addProductInBasket(Product product);

    public Basket updateBasket(String fields, int basketId);

    public void deleteBasketProduct(int productId);

    public String getTotalBasketSum(int basketId);

}
