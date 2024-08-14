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
public class CategoryRepoCustomImpl implements CategoryRepoCustom {

    @Autowired
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findAllCustom() {

        Query query = entityManager.createQuery("select c.id, c.name from Category as c");
        
        List<Object[]> queryResult = query.getResultList();
        List<Map<String, Object>> categories = new ArrayList<>();
        Map<String, Object> resultMap = fillMap();

        for (Object[] object : queryResult) {
            for (int i = 0; i < object.length; i++) {
                switch (i) {
                    case 0:
                        resultMap.put("id", object[i]);
                        break;
                    case 1:
                        resultMap.put("name", object[i]);
                        break;
                    default:
                        break;
                }
            }
            categories.add(resultMap);
            resultMap = fillMap();
        }

        return categories;

    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> findByIdCustom(int id) {
        
        Query query = entityManager.createQuery("select c.id, c.name from Category as c where id =: categoryId");
        query.setParameter("categoryId", id);

        List<Object[]> queryResult = query.getResultList();
        Map<String, Object> category = fillMap();

        for (int i = 0; i < queryResult.get(0).length; i++) {
            switch (i) {
                case 0:
                    category.put("id", queryResult.get(0)[i]);
                    break;
                case 1:
                    category.put("name", queryResult.get(0)[i]);
                    break;
                default:
                    break;
            }
        }

        return category;

    }

    public static Map<String, Object> fillMap() {

        Map<String, Object> map = new LinkedHashMap<>();
        Object tmp = new Object();

        map.put("id", tmp);
        map.put("name", tmp);

        return map;

    }

}
