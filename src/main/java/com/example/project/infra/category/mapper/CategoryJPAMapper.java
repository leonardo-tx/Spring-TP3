package com.example.project.infra.category.mapper;

import com.example.project.core.category.model.Category;
import com.example.project.core.generic.mapper.Mapper;
import com.example.project.infra.category.persistence.CategoryJPA;
import org.springframework.stereotype.Component;

@Component
public class CategoryJPAMapper implements Mapper<Category, CategoryJPA> {
    @Override
    public Category toModel(CategoryJPA entity) {
        return null;
    }

    @Override
    public CategoryJPA toEntity(Category model) {
        return null;
    }
}
