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

@Service
public class BasketServiceImpl extends BaseService implements BasketService {

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
        
        List<Basket> userBaskets = getUserBaskets(userId);
        List<Map<String, Object>> basketProducts = new ArrayList<>();
        Map<String, Object> resultMap = fillMap('b');
        Product product;

        for (Basket basket : userBaskets) {
            resultMap.put("id", basket.getId());
            resultMap.put("productId", basket.getProductId());

            product = productRepository.findById(basket.getProductId()).get();

            resultMap.put("productName", product.getName());
            resultMap.put("productPrice", product.getPrice());
            resultMap.put("productAmount", basket.getProductAmount());

            basketProducts.add(resultMap);

            resultMap = fillMap('b');
        }
        
        return basketProducts;

    }

    @Override
    public Map<String, Object> getBasketProduct(int productId) {
        
        return productRepoCustom.findByIdCustom(productId);

    }

    @Override
    public String addProductInBasket(String productData, int userId) {
        
        Map<String, Object> resultMap = jsonToMap(productData);
        User user = userRepository.findById(userId).get();
        Basket basket = new Basket();

        int productId = (Integer) resultMap.get("productId");
        int productAmount = (Integer) resultMap.get("productAmount");

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

    @Override
    public String updateBasketProduct(String fields, int basketId) {
        
        Map<String, Object> resultMap = jsonToMap(fields);

        Basket basket = basketRepository.findById(basketId).get();

        int productId = (Integer) resultMap.get("productId");
        int productAmount = (Integer) resultMap.get("productAmount");

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
        
        List<Basket> userBaskets = getUserBaskets(userId);

        List<Map<String, Object>> basketPriceList = new ArrayList<>();
        Map<String, Object> resultMap = fillMap('p');
        Product product;
        double totalSum = 0;

        for (Basket basket : userBaskets) {
            resultMap.put("productId", basket.getProductId());

            product = productRepository.findById(basket.getProductId()).get();

            resultMap.put("productName", product.getName());
            resultMap.put("productPrice", product.getPrice());
            resultMap.put("productAmount", basket.getProductAmount());

            totalSum += (product.getPrice() * basket.getProductAmount());

            basketPriceList.add(resultMap);
            resultMap = fillMap('p');
        }

        basketPriceList.add(Map.of("totalSum", totalSum));

        return basketPriceList;

    }

    public List<Basket> getUserBaskets(int userId) {

        User user = userRepository.findById(userId).get();

        List<Basket> allBaskets = basketRepository.findAll();
        List<Basket> userBaskets = new ArrayList<>();

        for (Basket basket : allBaskets) {
            for (User u : basket.getUsersBaskets()) {
                if (u.equals(user)) userBaskets.add(basket);
            }
        }

        return userBaskets;

    }

    public double getTotalSum(int userId) {

        List<Basket> userBaskets = getUserBaskets(userId);

        double totalSum = 0;
        int productId = 0;
        Product product;

        for (Basket basket : userBaskets) {
            productId = basket.getProductId();
            product = productRepository.findById(productId).get();
            totalSum += (product.getPrice() * basket.getProductAmount());
        }

        return totalSum;

    }

    public static Map<String, Object> fillMap(char type) {

        Map<String, Object> map = new LinkedHashMap<>();
        Object tmp = new Object();

        switch (type) {
            case 'p':
                map.put("productId", tmp);
                map.put("productName", tmp);
                map.put("productPrice", tmp);
                map.put("productAmount", tmp);
                break;
            case 'b':
                map.put("id", tmp);
                map.put("productId", tmp);
                map.put("productName", tmp);
                map.put("productPrice", tmp);
                map.put("productAmount", tmp);
                break;
            default:
                System.out.println("Такого поля нет");
                break;
        }

        return map;

    }

}
