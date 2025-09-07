package com.example.project.api.controller;

import com.example.project.api.request.product.dto.ProductCreateDTO;
import com.example.project.api.request.product.dto.ProductUpdateDTO;
import com.example.project.api.response.product.dto.ProductViewDTO;
import com.example.project.core.generic.mapper.InputMapper;
import com.example.project.core.generic.mapper.OutputMapper;
import com.example.project.core.product.model.Product;
import com.example.project.core.product.service.ProductService;
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

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private InputMapper<Product, ProductCreateDTO> productCreateMapper;

    @MockitoBean
    private InputMapper<Product, ProductUpdateDTO> productUpdateMapper;

    @MockitoBean
    private OutputMapper<Product, ProductViewDTO> productViewMapper;

    @Test
    void shouldReturnAll() throws Exception {
        List<Product> products = List.of(
                mock(Product.class),
                mock(Product.class)
        );
        List<ProductViewDTO> productViewDTOs = List.of(
                new ProductViewDTO(8L, "Product1", "Description1", new BigDecimal("100.00")),
                new ProductViewDTO(1L, "Product2", "Description2", new BigDecimal("200.00"))
        );

        when(productService.getAll()).thenReturn(products);
        for (int i = 0; i < products.size(); i++) {
            when(productViewMapper.toEntity(products.get(i))).thenReturn(productViewDTOs.get(i));
        }

        ResultActions resultActions = mockMvc.perform(get("/product"));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result", hasSize(2)));

        for (int i = 0; i < productViewDTOs.size(); i++) {
            ProductViewDTO product = productViewDTOs.get(i);
            String init = "result[" + i + "]";
            resultActions.andExpect(jsonPath(init + ".id").value(product.getId()))
                    .andExpect(jsonPath(init + ".name").value(product.getName()))
                    .andExpect(jsonPath(init + ".description").value(product.getDescription()))
                    .andExpect(jsonPath(init + ".price").value(product.getPrice()));
        }
    }

    @Test
    void shouldReturnEmptyListWhenNoProductsExist() throws Exception {
        when(productService.getAll()).thenReturn(List.of());

        ResultActions resultActions = mockMvc.perform(get("/product"));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result", hasSize(0)));
    }

    @Test
    void shouldReturnProductById() throws Exception {
        Long productId = 1L;
        Product product = mock(Product.class);
        ProductViewDTO productViewDTO = new ProductViewDTO(productId, "Product1", "Description1", new BigDecimal("100.00"));

        when(productService.getById(productId)).thenReturn(product);
        when(productViewMapper.toEntity(product)).thenReturn(productViewDTO);

        ResultActions resultActions = mockMvc.perform(get("/product/{id}", productId));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result.id").value(productId))
                .andExpect(jsonPath("result.name").value(productViewDTO.getName()))
                .andExpect(jsonPath("result.description").value(productViewDTO.getDescription()))
                .andExpect(jsonPath("result.price").value(productViewDTO.getPrice()));
    }

    @Test
    void shouldCreateProduct() throws Exception {
        ProductCreateDTO createDTO = new ProductCreateDTO("New Product", "New Description", new BigDecimal("150.00"));
        Product productToCreate = mock(Product.class);
        Product createdProduct = mock(Product.class);
        ProductViewDTO productViewDTO = new ProductViewDTO(1L, "New Product", "New Description", new BigDecimal("150.00"));

        when(productCreateMapper.toModel(createDTO)).thenReturn(productToCreate);
        when(productService.create(productToCreate)).thenReturn(createdProduct);
        when(productViewMapper.toEntity(createdProduct)).thenReturn(productViewDTO);

        ResultActions resultActions = mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.CREATED)
                .andExpect(jsonPath("result.id").value(1L))
                .andExpect(jsonPath("result.name").value(productViewDTO.getName()))
                .andExpect(jsonPath("result.description").value(productViewDTO.getDescription()))
                .andExpect(jsonPath("result.price").value(productViewDTO.getPrice()));
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        Long productId = 1L;
        ProductCreateDTO createDTO = new ProductCreateDTO("Updated Product", "Updated Description", new BigDecimal("250.00"));
        Product productToUpdate = mock(Product.class);
        Product updatedProduct = mock(Product.class);
        ProductViewDTO productViewDTO = new ProductViewDTO(productId, "Updated Product", "Updated Description", new BigDecimal("250.00"));

        when(productUpdateMapper.toModel(any(ProductUpdateDTO.class))).thenReturn(productToUpdate);
        when(productService.update(productToUpdate)).thenReturn(updatedProduct);
        when(productViewMapper.toEntity(updatedProduct)).thenReturn(productViewDTO);

        ResultActions resultActions = mockMvc.perform(put("/product/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result.id").value(productId))
                .andExpect(jsonPath("result.name").value(productViewDTO.getName()))
                .andExpect(jsonPath("result.description").value(productViewDTO.getDescription()))
                .andExpect(jsonPath("result.price").value(productViewDTO.getPrice()));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        Long productId = 1L;
        Product product = mock(Product.class);

        when(productService.getById(productId)).thenReturn(product);

        ResultActions resultActions = mockMvc.perform(delete("/product/{id}", productId));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result").value(nullValue()));

        verify(productService, times(1)).delete(product);
    }
}
