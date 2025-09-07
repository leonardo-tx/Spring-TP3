package com.example.project.core.category.repository;

import com.example.project.core.category.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> findAll();
    Optional<Category> findById(Long id);
    boolean existsById(Long id);
    void delete(Category category);
    Category save(Category category);
}
