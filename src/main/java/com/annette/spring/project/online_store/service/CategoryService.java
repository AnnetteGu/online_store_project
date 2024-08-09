package com.annette.spring.project.online_store.service;

import java.util.List;

import com.annette.spring.project.online_store.entity.Category;

public interface CategoryService {

    public List<Category> getAllCategories();

    public Category getCategory(int id);

    public Category addCategory(Category category);

    public Category updateCategory(String fields, int id);

    public void deleteCategory(int id);

}
