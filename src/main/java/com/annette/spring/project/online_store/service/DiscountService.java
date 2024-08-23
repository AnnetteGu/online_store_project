package com.annette.spring.project.online_store.service;

import java.util.List;
import java.util.Map;

import com.annette.spring.project.online_store.entity.Discount;

public interface DiscountService {

    public List<Discount> getAllDiscounts();

    public Discount getDiscount(int id);

    public Discount addDiscount(Discount discount);

    public Discount updateDiscount(String fields, int id);

    public void deleteDiscount(int id);

    public List<Map<String, Object>> getAllModifiedProducts();

}
