package com.example.project.api.request.supplier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierCreateDTO {
    private String name;
    private String document;
    private String email;
    private String phone;
}
