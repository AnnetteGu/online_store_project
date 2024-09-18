package com.annette.spring.project.online_store.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.annette.spring.project.online_store.entity.Discount;
import com.annette.spring.project.online_store.repository.DiscountRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseService {

    @Autowired
    DiscountRepository discountRepository;

    @SuppressWarnings("unchecked")
    public Map<String, Object> jsonToMap(String json) {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> resultMap = new LinkedHashMap<>();

        try {
            resultMap = objectMapper.readValue(json, LinkedHashMap.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return resultMap;

    }

    public Discount getActiveDiscount() {

        List<Discount> allDiscounts = discountRepository.findAll();
        Discount activeDiscount = null;

        for (Discount discount : allDiscounts) {
            if (discount.getIsActive())
                activeDiscount = discountRepository.findById(discount.getId()).get();
        }

        return activeDiscount;

    }

    public int percent(int sum, int disc) {

        return (int) (sum - (sum * ((double) disc / 100)));

    }

}
