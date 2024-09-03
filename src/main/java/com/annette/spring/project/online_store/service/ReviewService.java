package com.annette.spring.project.online_store.service;

import java.util.List;

import com.annette.spring.project.online_store.entity.Review;

public interface ReviewService {

    public List<Review> getAllProductReviews(int productId);

    public List<Review> getAllUserReviews(int userId);

    public Review addReview(String data, int userId);

    public Review updateReview(String data);

    public String deleteReview(int id);

}
