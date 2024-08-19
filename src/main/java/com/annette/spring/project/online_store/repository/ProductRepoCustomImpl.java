package com.annette.spring.project.online_store.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository
public class ProductRepoCustomImpl implements ProductRepoCustom {

    @Autowired
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findAllCustom() {
        
        Query query = entityManager.createQuery(
            "select p.id, p.sellerId, p.name, p.price, p.isAllowed from Product as p");

        List<Object[]> queryResult = query.getResultList();
        List<Map<String, Object>> products = new ArrayList<>();
        Map<String, Object> resultMap = fillMap();

        for (Object[] object : queryResult) {
            for (int i = 0; i < object.length; i++) {
                switch (i) {
                    case 0:
                        resultMap.put("id", object[i]);
                        break;
                    case 1:
                        resultMap.put("sellerId", object[i]);
                        break;
                    case 2:
                        resultMap.put("name", object[i]);
                        break;
                    case 3:
                        resultMap.put("price", object[i]);
                        break;
                    case 4:
                        resultMap.put("isAllowed", object[i]);
                        break;
                    default:
                        break;
                }
            }
            products.add(resultMap);
            resultMap = fillMap();
        }

        return products;

    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> findByIdCustom(int id) {
        
        Query query = entityManager.createQuery(
            "select p.id, p.sellerId, p.name, p.price, p.isAllowed from Product as p where id =: productId");

        query.setParameter("productId", id);
        
        List<Object[]> queryResult = query.getResultList();
        Map<String, Object> product = fillMap();

        for (int i = 0; i < queryResult.get(0).length; i++) {
            switch (i) {
                case 0:
                    product.put("id", queryResult.get(0)[i]);
                    break;
                case 1:
                    product.put("sellerId", queryResult.get(0)[i]);
                    break;
                case 2:
                    product.put("name", queryResult.get(0)[i]);
                    break;
                case 3:
                    product.put("price", queryResult.get(0)[i]);
                    break;
                case 4:
                    product.put("isAllowed", queryResult.get(0)[i]);
                    break;
                default:
                    break;
            }
        }

        return product;

    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findByCategory(String category) {

        Query query = entityManager.createQuery(
            "select p.id, p.sellerId, p.name, p.price, p.isAllowed from Product as p " +
            "inner join p.categories as pc " +
            "where :categoryName in(select pc.name from p.categories as pc)");
        
        query.setParameter("categoryName", category);

        List<Object[]> queryResult = query.getResultList();

        for (Object[] object : queryResult) {
            for (Object o : object) {
                System.out.println(o);
            }
            System.out.println();
        }

        List<Map<String, Object>> products = new ArrayList<>();
        Map<String, Object> resultMap = fillMap();

        for (Object[] object : queryResult) {
            for (int i = 0; i < object.length; i++) {
                switch (i) {
                    case 0:
                        resultMap.put("id", object[i]);
                        break;
                    case 1:
                        resultMap.put("sellerId", object[i]);
                        break;
                    case 2:
                        resultMap.put("name", object[i]);
                        break;
                    case 3:
                        resultMap.put("price", object[i]);
                        break;
                    case 4:
                        resultMap.put("isAllowed", object[i]);
                        break;
                    default:
                        break;
                }
            }
            products.add(resultMap);
            resultMap = fillMap();
        }

        return products;

    }

    public static Map<String, Object> fillMap() {
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
