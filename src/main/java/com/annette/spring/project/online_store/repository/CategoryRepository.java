package com.annette.spring.project.online_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.annette.spring.project.online_store.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
