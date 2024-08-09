package com.annette.spring.project.online_store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annette.spring.project.online_store.entity.Basket;
import com.annette.spring.project.online_store.entity.Product;
import com.annette.spring.project.online_store.repository.BasketRepository;

@Service
public class BasketServiceImpl implements BasketService {

    @Autowired
    private BasketRepository basketRepository;

    @Override
    public List<Product> getAllBasketProducts(int basketId) {
        throw new UnsupportedOperationException("Unimplemented method 'getAllBasketProducts'");
    }

    @Override
    public Product getBasketProduct(int productId) {
        throw new UnsupportedOperationException("Unimplemented method 'getBasketProduct'");
    }

    @Override
    public Product addProductInBasket(Product product) {
        throw new UnsupportedOperationException("Unimplemented method 'addProductInBasket'");
    }

    @Override
    public Basket updateBasket(String fields, int basketId) {
        throw new UnsupportedOperationException("Unimplemented method 'updateBasket'");
    }

    @Override
    public void deleteBasketProduct(int productId) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteBasketProduct'");
    }

    @Override
    public String getTotalBasketSum(int basketId) {
        throw new UnsupportedOperationException("Unimplemented method 'getTotalBasketSum'");
    }

}
