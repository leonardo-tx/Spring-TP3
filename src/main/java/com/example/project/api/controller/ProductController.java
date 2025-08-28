package com.example.project.api.controller;

import com.example.project.api.request.product.dto.ProductCreateDTO;
import com.example.project.api.request.product.dto.ProductUpdateDTO;
import com.example.project.api.response.ApiResponse;
import com.example.project.api.response.product.dto.ProductViewDTO;
import com.example.project.core.product.model.Product;
import com.example.project.core.generic.mapper.InputMapper;
import com.example.project.core.generic.mapper.OutputMapper;
import com.example.project.core.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final InputMapper<Product, ProductCreateDTO> productCreateMapper;
    private final InputMapper<Product, ProductUpdateDTO> productUpdateMapper;
    private final OutputMapper<Product, ProductViewDTO> productViewMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductViewDTO>>> getAll() {
        List<ProductViewDTO> productViewDTOs = productService.getAll()
                .stream()
                .map(productViewMapper::toEntity)
                .toList();
        return ApiResponse.success(productViewDTOs).createResponse(HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<ProductViewDTO>> getById(@PathVariable("id") Long id) {
        Product product = productService.getById(id);
        ProductViewDTO productViewDTO = productViewMapper.toEntity(product);

        return ApiResponse.success(productViewDTO).createResponse(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductViewDTO>> create(@RequestBody ProductCreateDTO form) {
        Product product = productCreateMapper.toModel(form);
        Product createdProduct = productService.create(product);
        ProductViewDTO productViewDTO = productViewMapper.toEntity(createdProduct);

        return ApiResponse.success(productViewDTO).createResponse(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<ProductViewDTO>> updateById(@PathVariable("id") Long id, @RequestBody ProductUpdateDTO form) {
        Product partialProduct = productUpdateMapper.toModel(form);
        Product targetProduct = productService.getById(id);
        Product updatedProduct = productService.update(partialProduct, targetProduct);
        ProductViewDTO productViewDTO = productViewMapper.toEntity(updatedProduct);

        return ApiResponse.success(productViewDTO).createResponse(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Object>> deleteById(@PathVariable("id") Long id) {
        Product product = productService.getById(id);
        productService.delete(product);

        return ApiResponse.success(null).createResponse(HttpStatus.OK);
    }
}
