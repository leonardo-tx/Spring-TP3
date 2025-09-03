package com.example.project.api.controller;

import com.example.project.api.request.category.dto.CategoryCreateDTO;
import com.example.project.api.request.category.dto.CategoryUpdateDTO;
import com.example.project.api.response.ApiResponse;
import com.example.project.api.response.category.dto.CategoryViewDTO;
import com.example.project.core.category.model.Category;
import com.example.project.core.category.service.CategoryService;
import com.example.project.core.generic.mapper.InputMapper;
import com.example.project.core.generic.mapper.OutputMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final InputMapper<Category, CategoryCreateDTO> categoryCreateMapper;
    private final InputMapper<Category, CategoryUpdateDTO> categoryUpdateMapper;
    private final OutputMapper<Category, CategoryViewDTO> categoryViewMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryViewDTO>>> getAll() {
        List<CategoryViewDTO> categoryViewDTOs = categoryService.getAll()
                .stream()
                .map(categoryViewMapper::toEntity)
                .toList();
        return ApiResponse.success(categoryViewDTOs).createResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryViewDTO>> getById(@PathVariable("id") Long id) {
        Category category = categoryService.getById(id);
        CategoryViewDTO categoryViewDTO = categoryViewMapper.toEntity(category);

        return ApiResponse.success(categoryViewDTO).createResponse(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryViewDTO>> create(@RequestBody CategoryCreateDTO form) {
        Category category = categoryCreateMapper.toModel(form);
        Category createdCategory = categoryService.create(category);
        CategoryViewDTO categoryViewDTO = categoryViewMapper.toEntity(createdCategory);

        return ApiResponse.success(categoryViewDTO).createResponse(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryViewDTO>> updateById(@PathVariable("id") Long id, @RequestBody CategoryCreateDTO form) {
        CategoryUpdateDTO updateDTO = new CategoryUpdateDTO(id, form.getName());
        Category changedCategory = categoryUpdateMapper.toModel(updateDTO);
        Category updatedCategory = categoryService.update(changedCategory);
        CategoryViewDTO categoryViewDTO = categoryViewMapper.toEntity(updatedCategory);

        return ApiResponse.success(categoryViewDTO).createResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteById(@PathVariable("id") Long id) {
        Category category = categoryService.getById(id);
        categoryService.delete(category);

        return ApiResponse.success(null).createResponse(HttpStatus.OK);
    }
}
