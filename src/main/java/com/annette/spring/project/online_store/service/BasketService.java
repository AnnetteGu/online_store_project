package com.annette.spring.project.online_store.service;

import java.util.List;
import java.util.Map;

public interface BasketService {

    public List<Map<String, Object>> getAllBasketProducts(int userId);

    public Map<String, Object> getBasketProduct(int productId);

    public String addProductInBasket(String productData, int userId);

    public String updateBasketProduct(String fields, int basketId);

    public void deleteBasketProduct(int productId);

    public List<Map<String, Object>> getTotalBasketSum(int userId);

}
