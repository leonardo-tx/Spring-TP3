package com.example.project.core.category.service;

import com.example.project.core.category.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    Category getById(Long id);
    void delete(Category category);
    Category create(Category category);
    Category update(Category category);
}
