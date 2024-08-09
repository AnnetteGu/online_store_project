package com.annette.spring.project.online_store.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annette.spring.project.online_store.entity.Category;
import com.annette.spring.project.online_store.repository.CategoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        
        return categoryRepository.findAll();

    }

    @Override
    public Category getCategory(int id) {
        
        Optional<Category> optional = categoryRepository.findById(id);
        Category category = null;

        if (optional.isPresent()) category = optional.get();

        return category;

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
