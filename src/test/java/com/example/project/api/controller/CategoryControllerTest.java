package com.example.project.api.controller;

import com.example.project.api.request.category.dto.CategoryCreateDTO;
import com.example.project.api.request.category.dto.CategoryUpdateDTO;
import com.example.project.api.response.category.dto.CategoryViewDTO;
import com.example.project.core.category.model.Category;
import com.example.project.core.category.service.CategoryService;
import com.example.project.core.generic.mapper.InputMapper;
import com.example.project.core.generic.mapper.OutputMapper;
import com.example.project.test.util.MockMvcHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockitoBean
    private CategoryService categoryService;

    @MockitoBean
    private InputMapper<Category, CategoryCreateDTO> categoryCreateMapper;

    @MockitoBean
    private InputMapper<Category, CategoryUpdateDTO> categoryUpdateMapper;

    @MockitoBean
    private OutputMapper<Category, CategoryViewDTO> categoryViewMapper;

    @Test
    void shouldReturnAll() throws Exception {
        List<Category> categories = List.of(
                mock(Category.class),
                mock(Category.class)
        );
        List<CategoryViewDTO> categoryViewDTOs = List.of(
                new CategoryViewDTO(8L, "Moderno"),
                new CategoryViewDTO(1L, "Minimalista")
        );

        when(categoryService.getAll()).thenReturn(categories);
        for (int i = 0; i < categories.size(); i++) {
            when(categoryViewMapper.toEntity(categories.get(i))).thenReturn(categoryViewDTOs.get(i));
        }

        ResultActions resultActions = mockMvc.perform(get("/category"));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result", hasSize(2)));

        for (int i = 0; i < categoryViewDTOs.size(); i++) {
            CategoryViewDTO category = categoryViewDTOs.get(i);
            String init = "result[" + i + "]";
            resultActions.andExpect(jsonPath(init + ".id").value(category.getId()))
                    .andExpect(jsonPath(init + ".name").value(category.getName()));
        }
    }

    @Test
    void shouldReturnEmptyListWhenNoCategoriesExist() throws Exception {
        when(categoryService.getAll()).thenReturn(List.of());

        ResultActions resultActions = mockMvc.perform(get("/category"));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result", hasSize(0)));
    }

    @Test
    void shouldReturnCategoryById() throws Exception {
        Long categoryId = 1L;
        Category category = mock(Category.class);
        CategoryViewDTO categoryViewDTO = new CategoryViewDTO(categoryId, "Minimalista");

        when(categoryService.getById(categoryId)).thenReturn(category);
        when(categoryViewMapper.toEntity(category)).thenReturn(categoryViewDTO);
        ResultActions resultActions = mockMvc.perform(get("/category/{id}", categoryId));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result.id").value(categoryId))
                .andExpect(jsonPath("result.name").value(categoryViewDTO.getName()));
    }

    @Test
    void shouldCreateCategory() throws Exception {
        CategoryCreateDTO createDTO = new CategoryCreateDTO("Nova Categoria");
        Category categoryToCreate = mock(Category.class);
        Category createdCategory = mock(Category.class);
        CategoryViewDTO categoryViewDTO = new CategoryViewDTO(1L, "Nova Categoria");

        when(categoryCreateMapper.toModel(createDTO)).thenReturn(categoryToCreate);
        when(categoryService.create(categoryToCreate)).thenReturn(createdCategory);
        when(categoryViewMapper.toEntity(createdCategory)).thenReturn(categoryViewDTO);

        ResultActions resultActions = mockMvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.CREATED)
                .andExpect(jsonPath("result.id").value(categoryViewDTO.getId()))
                .andExpect(jsonPath("result.name").value(categoryViewDTO.getName()));
    }

    @Test
    void shouldUpdateCategory() throws Exception {
        Long categoryId = 1L;
        CategoryCreateDTO createDTO = new CategoryCreateDTO("Categoria Atualizada");
        Category categoryToUpdate = mock(Category.class);
        Category updatedCategory = mock(Category.class);
        CategoryViewDTO categoryViewDTO = new CategoryViewDTO(categoryId, "Categoria Atualizada");

        when(categoryUpdateMapper.toModel(any(CategoryUpdateDTO.class))).thenReturn(categoryToUpdate);
        when(categoryService.update(categoryToUpdate)).thenReturn(updatedCategory);
        when(categoryViewMapper.toEntity(updatedCategory)).thenReturn(categoryViewDTO);

        ResultActions resultActions = mockMvc.perform(put("/category/{id}", categoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result.id").value(categoryId))
                .andExpect(jsonPath("result.name").value(categoryViewDTO.getName()));
    }

    @Test
    void shouldDeleteCategory() throws Exception {
        Long categoryId = 1L;
        Category category = mock(Category.class);

        when(categoryService.getById(categoryId)).thenReturn(category);

        ResultActions resultActions = mockMvc.perform(delete("/category/{id}", categoryId));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result").value(nullValue()));

        verify(categoryService, times(1)).delete(category);
    }
}
