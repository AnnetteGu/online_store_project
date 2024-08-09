package com.annette.spring.project.online_store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.annette.spring.project.online_store.entity.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    public Optional<Product> findByName(String name); 

}
