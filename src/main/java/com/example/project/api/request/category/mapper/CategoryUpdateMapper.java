package com.example.project.api.request.category.mapper;

import com.example.project.api.request.category.dto.CategoryUpdateDTO;
import com.example.project.core.category.model.Category;
import com.example.project.core.generic.mapper.InputMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryUpdateMapper implements InputMapper<Category, CategoryUpdateDTO> {
    @Override
    public Category toModel(CategoryUpdateDTO entity) {
        return null;
    }
}
