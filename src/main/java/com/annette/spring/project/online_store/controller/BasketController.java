package com.annette.spring.project.online_store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.annette.spring.project.online_store.entity.User;
import com.annette.spring.project.online_store.exception_handling.UserBadAuthoritiesException;
import com.annette.spring.project.online_store.service.BasketService;
import com.annette.spring.project.online_store.service.UserService;

@RestController
@RequestMapping("/api")
public class BasketController {

    @Autowired
    private BasketService basketService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @GetMapping("/baskets/{userId}")
    public List<Map<String, Object>> getAllBasketProducts(@PathVariable int userId, Authentication authentication) {
        
        User currentUser = userService.getUserByLogin(authentication.getName());

        if (currentUser.getId() == userId) 
            return basketService.getAllBasketProducts(userId);
        else
            throw new UserBadAuthoritiesException("Вы не можете получить товары из корзины этого пользователя");

    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @GetMapping("/baskets/user-product")
    public Map<String, Object> getBasketProduct(@RequestParam(name = "userId") int userId,
        @RequestParam(name = "prodId") int productId, Authentication authentication) {

        User currentUser = userService.getUserByLogin(authentication.getName());

        if (currentUser.getId() == userId) 
            return basketService.getBasketProduct(productId);
        else
            throw new UserBadAuthoritiesException("Вы не можете получить этот товар из корзины этого пользователя");

    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @PostMapping("/baskets")
    public String addProductInBasket(@RequestParam(name = "user") int userId,
        @RequestBody String productData, Authentication authentication) {

        User currentUser = userService.getUserByLogin(authentication.getName());

        if (currentUser.getId() == userId)    
            return basketService.addProductInBasket(productData, userId);
        else
            throw new UserBadAuthoritiesException("Вы не можете добавить товар в корзину к этому пользователю");

    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @PutMapping("/baskets")
    public String updateBasket(@RequestParam(name = "userId") int userId,
        @RequestParam(name = "basketId") int basketId, @RequestBody String fields, Authentication authentication) {

        User currentUser = userService.getUserByLogin(authentication.getName());

        if (currentUser.getId() == userId) 
            return basketService.updateBasketProduct(fields, basketId);
        else
            throw new UserBadAuthoritiesException("Вы не можете изменить товар в корзине этого пользователя");

    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @DeleteMapping("/baskets/user-product")
    public String deleteBasketProduct(@RequestParam(name = "user") int userId,
        @RequestParam(name = "prodId") int productId, Authentication authentication) {

        User currentUser = userService.getUserByLogin(authentication.getName());

        if (currentUser.getId() == userId) {
            basketService.deleteBasketProduct(productId);

            return "Товар с id = " + productId + " был удалён из корзины";
        }
        else
            throw new UserBadAuthoritiesException("Вы не можете удалить товар из корзины этого пользователя");

    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @GetMapping("/baskets/sum/{userId}")
    public List<Map<String, Object>> getTotalBasketSum(@PathVariable int userId, Authentication authentication) {

        User currentUser = userService.getUserByLogin(authentication.getName());

        if (currentUser.getId() == userId)
            return basketService.getTotalBasketSum(userId);
        else
            throw new UserBadAuthoritiesException("Вы не можете получить итоговую сумму корзины этого пользователя");
    }

}
