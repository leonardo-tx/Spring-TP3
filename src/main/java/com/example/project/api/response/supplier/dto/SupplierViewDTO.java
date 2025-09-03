package com.example.project.api.response.supplier.dto;

import com.example.project.core.generic.model.EntityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierViewDTO {
    private Long id;
    private String name;
    private String document;
    private EntityType entityType;
    private String email;
    private String phone;
}
