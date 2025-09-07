package com.example.project.api.response.employee.mapper;

import com.example.project.api.response.employee.dto.EmployeeViewDTO;
import com.example.project.core.employee.model.ContractType;
import com.example.project.core.employee.model.Employee;
import com.example.project.core.generic.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class EmployeeViewMapperTest {
    @InjectMocks
    private EmployeeViewMapper employeeViewMapper;

    @Test
    void shouldParseSuccessfully() {
        Employee employee = new Employee(
                1L,
                Money.fromInfra(new BigDecimal("5000.00")),
                LocalDate.of(2025, 1, 15),
                ContractType.PJ,
                EntityName.fromInfra("Gabriela Reis"),
                EntityDocument.fromInfra("44873708000122"),
                Email.fromInfra("gabriela@tatu.com"),
                Phone.fromInfra("11988888888")
        );

        EmployeeViewDTO result = employeeViewMapper.toEntity(employee);

        assertNotNull(result);
        assertEquals(employee.getId(), result.getId());
        assertEquals(employee.getSalary().getValue(), result.getSalary());
        assertEquals(employee.getHireDate(), result.getHireDate());
        assertEquals(employee.getContractType(), result.getContractType());
        assertEquals(employee.getName().getValue(), result.getName());
        assertEquals(employee.getDocument().getValue(), result.getDocument());
        assertEquals(EntityType.LEGAL, result.getEntityType());
        assertEquals(employee.getEmail().getValue(), result.getEmail());
        assertEquals(employee.getPhone().getValue(), result.getPhone());
    }
}
