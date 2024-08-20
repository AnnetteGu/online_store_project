package com.annette.spring.project.online_store.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annette.spring.project.online_store.entity.Basket;
import com.annette.spring.project.online_store.entity.Product;
import com.annette.spring.project.online_store.entity.User;
import com.annette.spring.project.online_store.repository.BasketRepository;
import com.annette.spring.project.online_store.repository.ProductRepoCustom;
import com.annette.spring.project.online_store.repository.ProductRepository;
import com.annette.spring.project.online_store.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BasketServiceImpl implements BasketService {

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductRepoCustom productRepoCustom;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Map<String, Object>> getAllBasketProducts(int userId) {
        
        User user = userRepository.findById(userId).get();

        List<Basket> allBaskets = basketRepository.findAll();
        List<Basket> userBaskets = new ArrayList<>();

        for (Basket basket : allBaskets) {
            for (User u : basket.getUsersBaskets()) {
                if (u.equals(user)) userBaskets.add(basket);
            }
        }

        List<Map<String, Object>> basketProducts = new ArrayList<>();
        Map<String, Object> resultMap = fillBasketListMap();
        Product product;

        for (Basket basket : userBaskets) {
            resultMap.put("id", basket.getId());
            resultMap.put("productId", basket.getProductId());

            product = productRepository.findById(basket.getProductId()).get();

            resultMap.put("productName", product.getName());
            resultMap.put("productPrice", product.getPrice());
            resultMap.put("productAmount", basket.getProductAmount());

            basketProducts.add(resultMap);

            resultMap = fillBasketListMap();
        }
        
        return basketProducts;

    }

    @Override
    public Map<String, Object> getBasketProduct(int productId) {
        
        return productRepoCustom.findByIdCustom(productId);

    }

    @SuppressWarnings("unchecked")
    @Override
    public String addProductInBasket(String productData, int userId) {
        
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> resultMap = new LinkedHashMap<>();
        User user = userRepository.findById(userId).get();
        Basket basket = new Basket();

        try {
            resultMap = objectMapper.readValue(productData, LinkedHashMap.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        int productId = Integer.parseInt(resultMap.get("productId"));
        int productAmount = Integer.parseInt(resultMap.get("productAmount"));

        basket.setProductId(productId);
        basket.setProductAmount(productAmount);

        List<User> users;

        if (basket.getUsersBaskets() != null)
            users = basket.getUsersBaskets();
        else
            users = new ArrayList<>();

        users.add(user);

        basket.setUsersBaskets(users);

        basketRepository.save(basket);

        return "Товар с id = " + productId + " был добавлен в корзину в количестве " + productAmount;

    }

    @SuppressWarnings("unchecked")
    @Override
    public String updateBasketProduct(String fields, int basketId) {
        
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> resultMap = new LinkedHashMap<>();
        
        try {
            resultMap = objectMapper.readValue(fields, LinkedHashMap.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Basket basket = basketRepository.findById(basketId).get();

        int productId = Integer.parseInt(resultMap.get("productId"));
        int productAmount = Integer.parseInt(resultMap.get("productAmount"));

        basket.setProductId(productId);
        basket.setProductAmount(productAmount);

        basketRepository.save(basket);

        return "Корзина с id = " + basketId + " была обновлена";

    }

    @Override
    public void deleteBasketProduct(int productId) {
        
        Basket basket = basketRepository.findByProductId(productId).get();

        basketRepository.deleteById(basket.getId());

    }

    @Override
    public List<Map<String, Object>> getTotalBasketSum(int userId) {
        
        User user = userRepository.findById(userId).get();

        List<Basket> allBaskets = basketRepository.findAll();
        List<Basket> userBaskets = new ArrayList<>();

        for (Basket basket : allBaskets) {
            for (User u : basket.getUsersBaskets()) {
                if (u.equals(user)) userBaskets.add(basket);
            }
        }

        List<Map<String, Object>> basketPriceList = new ArrayList<>();
        Map<String, Object> resultMap = fillPriceMap();
        Product product;
        int totalSum = 0;

        for (Basket basket : userBaskets) {
            resultMap.put("productId", basket.getProductId());

            product = productRepository.findById(basket.getProductId()).get();

            resultMap.put("productName", product.getName());
            resultMap.put("productPrice", product.getPrice());
            resultMap.put("productAmount", basket.getProductAmount());

            totalSum += (product.getPrice() * basket.getProductAmount());

            basketPriceList.add(resultMap);
            resultMap = fillPriceMap();
        }

        basketPriceList.add(Map.of("totalSum", totalSum));

        return basketPriceList;

    }

    public static Map<String, Object> fillPriceMap() {

        Map<String, Object> map = new LinkedHashMap<>();
        Object tmp = new Object();

        map.put("productId", tmp);
        map.put("productName", tmp);
        map.put("productPrice", tmp);
        map.put("productAmount", tmp);

        return map;

    }

    public static Map<String, Object> fillBasketListMap() {

        Map<String, Object> map = new LinkedHashMap<>();
        Object tmp = new Object();

        map.put("id", tmp);
        map.put("productId", tmp);
        map.put("productName", tmp);
        map.put("productPrice", tmp);
        map.put("productAmount", tmp);

        return map;

    }

}
