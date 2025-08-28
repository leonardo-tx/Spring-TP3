package com.example.project.core.category.factory;

import com.example.project.core.category.model.Category;

public interface CategoryFactory {
    Category create(CategoryParams params);
}
