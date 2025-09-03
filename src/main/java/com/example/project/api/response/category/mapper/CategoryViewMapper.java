package com.example.project.api.response.category.mapper;

import com.example.project.api.response.category.dto.CategoryViewDTO;
import com.example.project.core.category.model.Category;
import com.example.project.core.generic.mapper.OutputMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryViewMapper implements OutputMapper<Category, CategoryViewDTO> {
    @Override
    public CategoryViewDTO toEntity(Category model) {
        return new CategoryViewDTO(model.getId(), model.getName().getValue());
    }
}
