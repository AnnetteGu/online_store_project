package com.annette.spring.project.online_store.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.annette.spring.project.online_store.entity.Discount;

public class BaseRepository {

    @Autowired
    private DiscountRepository discountRepository;

    public Discount getActiveDiscount() {

        List<Discount> allDiscounts = discountRepository.findAll();
        Discount activeDiscount = null;

        for (Discount discount : allDiscounts) {
            if (discount.getIsActive())
                activeDiscount = discountRepository.findById(discount.getId()).get();
        }

        return activeDiscount;

    }

    public int percent(int sum, int disc) {

        return (int) (sum - (sum * ((double) disc / 100)));

    }

}
