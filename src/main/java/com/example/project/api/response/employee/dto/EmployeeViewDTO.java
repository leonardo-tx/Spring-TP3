package com.example.project.api.response.employee.dto;

import com.example.project.core.employee.model.ContractType;
import com.example.project.core.generic.model.EntityType;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#0.00")
    private BigDecimal salary;
    private LocalDate hireDate;
    private ContractType contractType;
    private String name;
    private String document;
    private EntityType entityType;
    private String email;
    private String phone;
}
