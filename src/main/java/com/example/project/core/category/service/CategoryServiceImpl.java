package com.example.project.core.category.service;

import com.example.project.core.category.model.Category;
import com.example.project.core.category.repository.CategoryRepository;
import com.example.project.core.generic.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (category == null) {
            throw new IllegalArgumentException("The category must not be null.");
        }
        if (category.getId() == null) {
            throw new IllegalArgumentException("The category to delete must have an identifier.");
        }
        categoryRepository.delete(category);
    }

    @Override
    public Category create(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("The category must not be null.");
        }
        if (category.getId() != null) {
            throw new IllegalArgumentException("The category to create must not have an identifier.");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category partialCategory, Category targetCategory) {
        // TODO
        return null;
    }
}
