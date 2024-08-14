package com.annette.spring.project.online_store.service;

import java.util.List;
import java.util.Map;

import com.annette.spring.project.online_store.entity.Category;

public interface CategoryService {

    public List<Map<String, Object>> getAllCategories();

    public Map<String, Object> getCategory(int id);

    public Category addCategory(Category category);

    public Category updateCategory(String fields, int id);

    public void deleteCategory(int id);

}
