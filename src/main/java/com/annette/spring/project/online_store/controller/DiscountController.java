package com.annette.spring.project.online_store.controller;

import java.util.List;
import java.util.Map;

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

import com.annette.spring.project.online_store.entity.Discount;
import com.annette.spring.project.online_store.service.DiscountService;

@RestController
@RequestMapping("/api")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/discounts")
    public List<Discount> getAllDiscounts() {

        return discountService.getAllDiscounts();
        
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/discounts/{id}")
    public Discount getDiscount(@PathVariable int id) {

        return discountService.getDiscount(id);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/discounts")
    public Discount addDiscount(@RequestBody Discount discount) {

        return discountService.addDiscount(discount);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/discounts/id")
    public Discount updateDiscount(@RequestBody String fields, @RequestParam int id) {

        return discountService.updateDiscount(fields, id);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/discounts/{id}")
    public String deleteDiscount(@PathVariable int id) {

        discountService.deleteDiscount(id);

        return "Скидка с id = " + id + " была удалена";

    }

    @GetMapping("/discounts/products")
    public List<Map<String, Object>> getAllModifiedProducts() {

        return discountService.getAllModifiedProducts();

    }

}
