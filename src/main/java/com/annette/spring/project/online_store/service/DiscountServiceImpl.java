package com.annette.spring.project.online_store.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annette.spring.project.online_store.entity.Discount;
import com.annette.spring.project.online_store.entity.Product;
import com.annette.spring.project.online_store.repository.DiscountRepository;
import com.annette.spring.project.online_store.repository.ProductRepoCustom;
import com.annette.spring.project.online_store.repository.ProductRepository;

@Service
public class DiscountServiceImpl extends BaseService implements DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductRepoCustom productRepoCustom;

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

    @Override
    public Discount updateDiscount(String fields, int id) {
        
        Map<String, Object> resultMap = jsonToMap(fields);

        Discount discount = discountRepository.findById(id).get();

        for (Map.Entry<String, Object> field : resultMap.entrySet()) {
            switch (field.getKey()) {
                case "name":
                    discount.setName((String) field.getValue());
                    break;
                case "size":
                    discount.setSize((Integer) field.getValue());
                    break;
                case "isActive":
                    discount.setIsActive((Boolean) field.getValue());
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
    public List<Map<String, Object>> getAllModifiedProducts() {
        
        List<Product> allProducts = productRepository.findAll();
        List<Map<String, Object>> products = new ArrayList<>();
        Map<String, Object> resultMap = fillMap();

        List<Discount> allDiscounts = discountRepository.findAll();
        Discount discount = null;

        for (int i = 0; i < allDiscounts.size(); i++) {
            if (allDiscounts.get(i).getIsActive())
                discount = discountRepository.findById(i).get();
        }

        int newPrice = 0;

        if (discount != null) {
            for (Product product : allProducts) {
                resultMap.put("id", product.getId());
                resultMap.put("sellerId", product.getSellerId());
                resultMap.put("name", product.getName());
                
                newPrice = percent(product.getPrice(), discount.getSize());

                resultMap.put("price", newPrice);
                resultMap.put("isAllowed", product.getIsAllowed());

                products.add(resultMap);
                resultMap = fillMap();
            }

            return products;
        }
        else return productRepoCustom.findAllCustom();

    }

    private static int percent(int sum, int disc) {

        return (int) (sum - (sum * ((double) disc / 100)));

    }

    private static Map<String, Object> fillMap() {

        Map<String, Object> map = new LinkedHashMap<>();
        Object tmp = new Object();

        map.put("id", tmp);
        map.put("sellerId", tmp);
        map.put("name", tmp);
        map.put("price", tmp);
        map.put("isAllowed", tmp);

        return map;

    }

}