package com.example.project.api.request.employee.dto;

import com.example.project.core.employee.model.ContractType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCreateDTO {
    private BigDecimal salary;
    private LocalDate hireDate;
    private ContractType contractType;
    private String name;
    private String document;
    private String email;
    private String phone;
}
