package com.annette.spring.project.online_store.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annette.spring.project.online_store.entity.Category;
import com.annette.spring.project.online_store.exception_handling.CategoryDuplicateException;
import com.annette.spring.project.online_store.repository.CategoryRepoCustom;
import com.annette.spring.project.online_store.repository.CategoryRepository;

@Service
public class CategoryServiceImpl extends BaseService implements CategoryService {

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

        List<Category> allCategories = categoryRepository.findAll();

        for (Category c : allCategories) {
            if (c.getName().equals(category.getName()))
                throw new CategoryDuplicateException("Такая категория уже есть");
        }
        
        return categoryRepository.save(category);

    }

    @Override
    public Category updateCategory(String fields, int id) {
        
        Map<String, Object> resultMap = jsonToMap(fields);

        Category category = categoryRepository.findById(id).get();
        category.setName((String) resultMap.get("name"));

        return categoryRepository.save(category);

    }

    @Override
    public void deleteCategory(int id) {
        
        categoryRepository.deleteById(id);

    }

}
