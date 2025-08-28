package com.example.project.api.request.category.mapper;

import com.example.project.api.request.category.dto.CategoryCreateDTO;
import com.example.project.core.category.model.Category;
import com.example.project.core.generic.mapper.InputMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryCreateMapper implements InputMapper<Category, CategoryCreateDTO> {
    @Override
    public Category toModel(CategoryCreateDTO entity) {
        return null;
    }
}
