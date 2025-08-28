package com.example.project.api.controller;

import com.example.project.api.request.supplier.dto.SupplierCreateDTO;
import com.example.project.api.request.supplier.dto.SupplierUpdateDTO;
import com.example.project.api.response.ApiResponse;
import com.example.project.api.response.supplier.dto.SupplierViewDTO;
import com.example.project.core.supplier.model.Supplier;
import com.example.project.core.generic.mapper.InputMapper;
import com.example.project.core.generic.mapper.OutputMapper;
import com.example.project.core.supplier.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/supplier")
public class SupplierController {
    private final SupplierService supplierService;
    private final InputMapper<Supplier, SupplierCreateDTO> supplierCreateMapper;
    private final InputMapper<Supplier, SupplierUpdateDTO> supplierUpdateMapper;
    private final OutputMapper<Supplier, SupplierViewDTO> supplierViewMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<List<SupplierViewDTO>>> getAll() {
        List<SupplierViewDTO> supplierViewDTOs = supplierService.getAll()
                .stream()
                .map(supplierViewMapper::toEntity)
                .toList();
        return ApiResponse.success(supplierViewDTOs).createResponse(HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<SupplierViewDTO>> getById(@PathVariable("id") Long id) {
        Supplier supplier = supplierService.getById(id);
        SupplierViewDTO supplierViewDTO = supplierViewMapper.toEntity(supplier);

        return ApiResponse.success(supplierViewDTO).createResponse(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SupplierViewDTO>> create(@RequestBody SupplierCreateDTO form) {
        Supplier supplier = supplierCreateMapper.toModel(form);
        Supplier createdSupplier = supplierService.create(supplier);
        SupplierViewDTO supplierViewDTO = supplierViewMapper.toEntity(createdSupplier);

        return ApiResponse.success(supplierViewDTO).createResponse(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<SupplierViewDTO>> updateById(@PathVariable("id") Long id, @RequestBody SupplierUpdateDTO form) {
        Supplier partialSupplier = supplierUpdateMapper.toModel(form);
        Supplier targetSupplier = supplierService.getById(id);
        Supplier updatedSupplier = supplierService.update(partialSupplier, targetSupplier);
        SupplierViewDTO supplierViewDTO = supplierViewMapper.toEntity(updatedSupplier);

        return ApiResponse.success(supplierViewDTO).createResponse(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Object>> deleteById(@PathVariable("id") Long id) {
        Supplier supplier = supplierService.getById(id);
        supplierService.delete(supplier);

        return ApiResponse.success(null).createResponse(HttpStatus.OK);
    }
}
