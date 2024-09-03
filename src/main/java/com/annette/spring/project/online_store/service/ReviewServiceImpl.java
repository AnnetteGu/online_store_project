package com.annette.spring.project.online_store.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annette.spring.project.online_store.entity.Review;
import com.annette.spring.project.online_store.repository.ReviewRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public List<Review> getAllProductReviews(int productId) {

        return getCustomReviews(productId, 'p');

    }

    @Override
    public List<Review> getAllUserReviews(int userId) {

        return getCustomReviews(userId, 'u');

    }

    @SuppressWarnings("unchecked")
    @Override
    public Review addReview(String data, int userId) {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> resultMap = new LinkedHashMap<>();

        try {
            resultMap = objectMapper.readValue(data, LinkedHashMap.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Review review = new Review();

        review.setProductId((Integer) resultMap.get("productId"));
        review.setUserId(userId);
        review.setGrade((Double) resultMap.get("grade"));
        review.setText((String) resultMap.get("text"));
        
        return reviewRepository.save(review);

    }

    @SuppressWarnings("unchecked")
    @Override
    public Review updateReview(String data) {
        
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> resultMap = new LinkedHashMap<>();

        try {
            resultMap = objectMapper.readValue(data, LinkedHashMap.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        int id = (Integer) resultMap.get("id");
        Review review = reviewRepository.findById(id).get();
        resultMap.remove("id");

        for (Map.Entry<String, Object> set : resultMap.entrySet()) {
            switch (set.getKey()) {
                case "text":
                    review.setText((String) set.getValue());
                    break;
                case "grade":
                    review.setGrade((Double) set.getValue());
                    break;
                default:
                    System.out.println("Такого поля нет");
                    break;
            }
        }

        return reviewRepository.save(review);

    }

    @Override
    public String deleteReview(int id) {
        
        reviewRepository.deleteById(id);

        return "Отзыв с id = " + id + " был удалён";
 
    }

    private List<Review> getCustomReviews(int id, char type) {

        List<Review> allReviews = reviewRepository.findAll();
        List<Review> customReviews = new ArrayList<>();

        for (Review review : allReviews) {
            switch (type) {
                case 'u':
                    if (review.getUserId() == id)
                        customReviews.add(review);
                    break;
                case 'p':
                    if (review.getProductId() == id)
                        customReviews.add(review);
                    break;
                default:
                    System.out.println("Такого варианта нет");
                    break;
            }    
        }
        
        return customReviews;

    }

}
