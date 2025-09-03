package com.example.project.core.category.factory;

import com.example.project.core.category.model.Category;
import com.example.project.core.category.model.CategoryName;
import org.springframework.stereotype.Component;

@Component
public class CategoryFactoryImpl implements CategoryFactory {
    @Override
    public Category create(CategoryParams params) {
        CategoryName name = CategoryName.valueOf(params.getName());
        return new Category(params.getId(), name);
    }
}
