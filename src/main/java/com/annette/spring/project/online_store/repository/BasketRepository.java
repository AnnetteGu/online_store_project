package com.annette.spring.project.online_store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.annette.spring.project.online_store.entity.Basket;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Integer> {

    Optional<Basket> findByProductId(int productId);

}
