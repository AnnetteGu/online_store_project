package com.annette.spring.project.online_store.service;

import java.util.List;

import com.annette.spring.project.online_store.entity.Discount;
import com.annette.spring.project.online_store.entity.Product;

public interface DiscountService {

    public List<Discount> getAllDiscounts();

    public Discount getDiscount(int id);

    public Discount addDiscount(Discount discount);

    public Discount updateDiscount(String fields, int id);

    public void deleteDiscount(int id);

    public List<Product> getAllModifiedProducts();

}
