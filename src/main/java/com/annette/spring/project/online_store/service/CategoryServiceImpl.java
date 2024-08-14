package com.annette.spring.project.online_store.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annette.spring.project.online_store.entity.Category;
import com.annette.spring.project.online_store.repository.CategoryRepoCustom;
import com.annette.spring.project.online_store.repository.CategoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryRepoCustom categoryRepoCustom;

    @Override
    public List<Map<String, Object>> getAllCategories() {
        
        return categoryRepoCustom.findAllCustom();

    }

    @Override
    public Map<String, Object> getCategory(int id) {

        return categoryRepoCustom.findByIdCustom(id);

    }

    @Override
    public Category addCategory(Category category) {
        
        return categoryRepository.save(category);

    }

    @SuppressWarnings("unchecked")
    @Override
    public Category updateCategory(String fields, int id) {
        
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> resultMap = new LinkedHashMap<>();

        try {
            resultMap = objectMapper.readValue(fields, LinkedHashMap.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }

        Category category = categoryRepository.findById(id).get();
        category.setName(resultMap.get("name"));

        return categoryRepository.save(category);

    }

    @Override
    public void deleteCategory(int id) {
        
        categoryRepository.deleteById(id);

    }

}
