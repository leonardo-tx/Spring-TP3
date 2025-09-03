package com.example.project.api.response.employee.dto;

import com.example.project.core.employee.model.ContractType;
import com.example.project.core.generic.model.EntityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeViewDTO {
    private Long id;
    private BigDecimal salary;
    private LocalDate hireDate;
    private ContractType contractType;
    private String name;
    private String document;
    private EntityType entityType;
    private String email;
    private String phone;
}
