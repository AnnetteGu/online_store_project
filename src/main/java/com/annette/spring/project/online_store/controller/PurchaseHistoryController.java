package com.annette.spring.project.online_store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.annette.spring.project.online_store.entity.PurchaseHistory;
import com.annette.spring.project.online_store.entity.User;
import com.annette.spring.project.online_store.exception_handling.UserBadAuthoritiesException;
import com.annette.spring.project.online_store.service.PurchaseHistoryService;
import com.annette.spring.project.online_store.service.UserService;

@RestController
@RequestMapping("/api")
public class PurchaseHistoryController {

    @Autowired
    private PurchaseHistoryService purchaseHistoryService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @GetMapping("/histories")
    public List<PurchaseHistory> getUserPurchaseHistories(
        @RequestParam(name = "id") int userId, Authentication authentication) {

        User user = userService.getUserByLogin(authentication.getName());

        if (user.getId() == userId)
            return purchaseHistoryService.getUserPurchaseHistory(userId);
        else
            throw new UserBadAuthoritiesException(
                "Вы не можете получить историю покупок этого пользователя");

    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @GetMapping("/histories/product")
    public Map<String, Object> getPurchaseHistoryProduct(@RequestParam(name = "id") int userId, 
        @RequestParam(name = "name") String productName, Authentication authentication) {

        User user = userService.getUserByLogin(authentication.getName());

        if (user.getId() == userId) 
            return purchaseHistoryService.getPurchaseHistoryProduct(userId, productName);
        else
            throw new UserBadAuthoritiesException(
                "Вы не можете получить товар из истории покупок этого пользователя");

    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @GetMapping("/histories/last")
    public PurchaseHistory getLastProductFromHistory(
        @RequestParam(name = "id") int userId, Authentication authentication) {

        User user = userService.getUserByLogin(authentication.getName());

        if (user.getId() == userId) 
            return purchaseHistoryService.getLastProductFromHistory(userId);
        else
            throw new UserBadAuthoritiesException(
                "Вы не можете получить последний товар из истории покупок этого пользователя");

    }

    @PreAuthorize("hasRole('ROLE_SELLER')")
    @GetMapping("/histories/seller")
    public List<Map<String, Object>> getSellerTotalSum(
        @RequestParam(name = "id") int sellerId, Authentication authentication) {

        User user = userService.getUserByLogin(authentication.getName());

        if (user.getId() == sellerId)
            return purchaseHistoryService.getSellerTotalSum(sellerId);
        else
            throw new UserBadAuthoritiesException(
                "Вы не можете получить сводку по выручке этого продавца");

    }

}
