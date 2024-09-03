package com.annette.spring.project.online_store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.annette.spring.project.online_store.entity.Review;
import com.annette.spring.project.online_store.entity.User;
import com.annette.spring.project.online_store.exception_handling.UserBadAuthoritiesException;
import com.annette.spring.project.online_store.service.ReviewService;
import com.annette.spring.project.online_store.service.UserService;

@RestController
@RequestMapping("/api")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @GetMapping("/reviews/product")
    public List<Review> getAllProductReviews(@RequestParam(name = "id") int productId) {

        return reviewService.getAllProductReviews(productId);

    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @GetMapping("/reviews/user")
    public List<Review> getAllUseReviews(@RequestParam(name = "id") int userId, 
        Authentication authentication) {

        User user = userService.getUserByLogin(authentication.getName());

        if (user.getId() == userId)
            return reviewService.getAllUserReviews(userId);
        else
            throw new UserBadAuthoritiesException(
                "Вы не можете получить отзывы этого пользователя");

    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @PostMapping("/reviews")
    public Review addReview(@RequestBody String data, @RequestParam(name = "id") int userId,
        Authentication authentication) {

        User user = userService.getUserByLogin(authentication.getName());

        if (user.getId() == userId)
            return reviewService.addReview(data, userId);
        else
            throw new UserBadAuthoritiesException(
                "Вы не можете написать отзыв от имени этого пользователя");

    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @PutMapping("/reviews")
    public Review updateReview(@RequestBody String data, @RequestParam(name = "id") int userId,
        Authentication authentication) {

        User user = userService.getUserByLogin(authentication.getName());

        if (user.getId() == userId)
            return reviewService.updateReview(data);
        else
            throw new UserBadAuthoritiesException(
                "Вы не можете обновить отзыв этого пользователя");

    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_SELLER', 'ROLE_ADMIN')")
    @DeleteMapping("/reviews")
    public String deleteReview(@RequestParam int id, @RequestParam(name = "user") int userId,
        Authentication authentication) {

        User user = userService.getUserByLogin(authentication.getName());

        if (user.getId() == userId)
            return reviewService.deleteReview(id);
        else
            throw new UserBadAuthoritiesException(
                "Вы не можете удалить отзыв этого пользователя");

    }

}
