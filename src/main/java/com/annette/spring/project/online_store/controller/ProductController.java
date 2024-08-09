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
import org.springframework.web.bind.annotation.RestController;

import com.annette.spring.project.online_store.entity.Product;
import com.annette.spring.project.online_store.service.ProductService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<Product> getAllProducts() {

        return productService.getAllProducts();

    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable int id) {

        return productService.getProduct(id);

    }

    @GetMapping("/products/search")
    public Product getProductByName(@RequestParam String name) {

        return productService.getProductByName(name);

    }

    @PreAuthorize("hasRole('ROLE_SELLER')")
    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {

        return productService.addProduct(product);

    }

    @PreAuthorize("hasRole('ROLE_SELLER')")
    @PostMapping("/products/category")
    public String addProductInCategory(@RequestBody String idData) {

        return productService.addProductCategory(idData);

    }

    @PreAuthorize("hasRole('ROLE_SELLER')")
    @PutMapping("/products/id")
    public Product updateProduct(@RequestBody String fields, @RequestParam int id) {

        return productService.updateProduct(fields, id);

    }

    @PreAuthorize("hasRole('ROLE_SELLER')")
    @DeleteMapping("/products/{id}")
    public String deleteProduct(@PathVariable int id) {

        productService.deleteProduct(id);

        return "Товар с id = " + id + " был удалён";

    }

}
