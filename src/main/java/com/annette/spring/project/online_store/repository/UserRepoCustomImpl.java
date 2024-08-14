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
public class UserRepoCustomImpl implements UserRepoCustom {

    @Autowired
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findAllCustom() {

        Query query = entityManager.createQuery(
            "select u.id, u.name, u.login, u.password, u.balance, u.role, u.accessMode from User as u");
        
        List<Object[]> queryResult = query.getResultList();

        Map<String, Object> resultMap = fillMap();
        List<Map<String, Object>> users = new ArrayList<>();

        for (Object[] object : queryResult) {
            for (int i = 0; i < object.length; i++) {
                switch (i) {
                    case 0:
                        resultMap.put("id", object[i]);
                        break;
                    case 1:
                        resultMap.put("name", object[i]);
                        break;
                    case 2:
                        resultMap.put("login", object[i]);
                        break;
                    case 3:
                        resultMap.put("password", object[i]);
                        break;
                    case 4:
                        resultMap.put("balance", object[i]);
                        break;
                    case 5:
                        resultMap.put("role", object[i]);
                        break;
                    case 6:
                        resultMap.put("accessMode", object[i]);
                        break;
                    default:
                        break;
                }
            }
            users.add(resultMap);
            resultMap = fillMap();
        }

        return users;

    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> findByIdCustom(int id) {
        
        Query query = entityManager.createQuery(
            "select u.id, u.name, u.login, u.password, u.balance, u.role, u.accessMode from User as u where id =: userId");
        
        query.setParameter("userId", id);

        List<Object[]> queryResult = query.getResultList();
        Map<String, Object> user = fillMap();

        for (int i = 0; i < queryResult.get(0).length; i++) {
            switch (i) {
                case 0:
                    user.put("id", queryResult.get(0)[i]);
                    break;
                case 1:
                    user.put("name", queryResult.get(0)[i]);
                    break;
                case 2:
                    user.put("login", queryResult.get(0)[i]);
                    break;
                case 3:
                    user.put("password", queryResult.get(0)[i]);
                    break;
                case 4:
                    user.put("balance", queryResult.get(0)[i]);
                    break;
                case 5:
                    user.put("role", queryResult.get(0)[i]);
                    break;
                case 6:
                    user.put("accessMode", queryResult.get(0)[i]);
                    break;
                default:
                    break;
            }
        }

        return user;

    }

    public static Map<String, Object> fillMap() {

        Map<String, Object> resultMap = new LinkedHashMap<>();
        Object tmp = new Object();

        resultMap.put("id", tmp);
        resultMap.put("name", tmp);
        resultMap.put("login", tmp);
        resultMap.put("password", tmp);
        resultMap.put("balance", tmp);
        resultMap.put("role", tmp);
        resultMap.put("accessMode", tmp);

        return resultMap;

    }

}
