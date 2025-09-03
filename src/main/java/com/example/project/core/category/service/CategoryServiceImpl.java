package com.example.project.core.category.service;

import com.example.project.core.category.model.Category;
import com.example.project.core.category.repository.CategoryRepository;
import com.example.project.core.generic.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new NotFoundException("category.not.found", "Could not find the category with the specified id.")
        );
    }

    @Override
    public void delete(Category category) {
        Objects.requireNonNull(category);
        if (category.getId() == null) {
            throw new IllegalArgumentException("The category to delete must have an identifier.");
        }
        categoryRepository.delete(category);
    }

    @Override
    public Category create(Category category) {
        Objects.requireNonNull(category);
        if (category.getId() != null) {
            throw new IllegalArgumentException("The category to create must not have an identifier.");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
        Objects.requireNonNull(category);
        if (category.getId() == null) {
            throw new IllegalArgumentException("The category to update must have an identifier.");
        }
        return categoryRepository.save(category);
    }
}
