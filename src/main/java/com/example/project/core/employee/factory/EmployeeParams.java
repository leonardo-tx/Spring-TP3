package com.example.project.core.employee.factory;

import com.example.project.core.employee.model.ContractType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public final class EmployeeParams {
    private final Long id;
    private final BigDecimal salary;
    private final LocalDate hireDate;
    private final ContractType contractType;
    private final String name;
    private final String document;
    private final String email;
    private final String phone;
}
