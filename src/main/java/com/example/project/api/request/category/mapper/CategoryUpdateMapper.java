package com.example.project.api.request.category.mapper;

import com.example.project.api.request.category.dto.CategoryUpdateDTO;
import com.example.project.core.category.factory.CategoryFactory;
import com.example.project.core.category.factory.CategoryParams;
import com.example.project.core.category.model.Category;
import com.example.project.core.generic.mapper.InputMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryUpdateMapper implements InputMapper<Category, CategoryUpdateDTO> {
    private final CategoryFactory categoryFactory;

    @Override
    public Category toModel(CategoryUpdateDTO entity) {
        CategoryParams params = new CategoryParams(entity.getId(), entity.getName());
        return categoryFactory.create(params);
    }
}
