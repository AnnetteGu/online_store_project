package com.annette.spring.project.online_store.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annette.spring.project.online_store.entity.Discount;
import com.annette.spring.project.online_store.entity.Product;
import com.annette.spring.project.online_store.repository.DiscountRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Override
    public List<Discount> getAllDiscounts() {

        return discountRepository.findAll();

    }

    @Override
    public Discount getDiscount(int id) {
        
        Optional<Discount> optional = discountRepository.findById(id);

        Discount discount = null;

        if (optional.isPresent()) discount = optional.get();

        return discount;

    }

    @Override
    public Discount addDiscount(Discount discount) {
        
        return discountRepository.save(discount);

    }

    @SuppressWarnings("unchecked")
    @Override
    public Discount updateDiscount(String fields, int id) {
        
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> resultMap = new LinkedHashMap<>();

        try {
            resultMap = objectMapper.readValue(fields, LinkedHashMap.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }

        Discount discount = discountRepository.findById(id).get();

        for (Map.Entry<String, String> field : resultMap.entrySet()) {
            switch (field.getKey()) {
                case "name":
                    discount.setName(field.getValue());
                    break;
                case "size":
                    discount.setSize(Integer.parseInt(field.getValue()));
                    break;
                case "isActive":
                    discount.setIsActive(Boolean.parseBoolean(field.getValue()));
                    break;
                default:
                    System.out.println("Такого поля нет");
                    break;
            }
        }

        return discountRepository.save(discount);

    }

    @Override
    public void deleteDiscount(int id) {
       
        discountRepository.deleteById(id);

    }

    @Override
    public List<Product> getAllModifiedProducts() {
        throw new UnsupportedOperationException("Unimplemented method 'getAllModifiedProducts'");
    }

}
