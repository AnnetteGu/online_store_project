package com.annette.spring.project.online_store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.annette.spring.project.online_store.entity.Category;
import com.annette.spring.project.online_store.service.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public List<Category> getAllCategories() {

        return categoryService.getAllCategories();

    }

    @PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_ADMIN')")
    @GetMapping("/categories/{id}")
    public Category getCategory(@PathVariable int id) {

        return categoryService.getCategory(id);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/categories")
    public Category addCategory(@RequestBody Category category) {

        return categoryService.addCategory(category);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/categories/id")
    public Category updateCategory(@RequestBody String fields, @RequestParam int id) {

        return categoryService.updateCategory(fields, id);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/categories/{id}")
    public String deleteCategory(@PathVariable int id) {

        categoryService.deleteCategory(id);

        return "Категория с id = " + id + " была удалена";

    }

}
