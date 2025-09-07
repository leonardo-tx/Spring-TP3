package com.example.project.infra.employee.mapper;

import com.example.project.core.employee.model.ContractType;
import com.example.project.core.employee.model.Employee;
import com.example.project.core.generic.model.*;
import com.example.project.infra.employee.persistence.EmployeeJPA;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class EmployeeJPAMapperTest {
    @InjectMocks
    private EmployeeJPAMapper employeeJPAMapper;

    @Test
    void shouldMapToModel() {
        EmployeeJPA entity = EmployeeJPA.builder()
                .id(1L)
                .salary(new BigDecimal("5000.00"))
                .hireDate(LocalDate.now())
                .contractType(ContractType.CLT)
                .name("Funcionário")
                .document("32585542009")
                .email("employee@company.com")
                .phone("99999999")
                .build();

        Employee model = employeeJPAMapper.toModel(entity);

        assertEquals(entity.getId(), model.getId());
        assertEquals(entity.getSalary(), model.getSalary().getValue());
        assertEquals(entity.getHireDate(), model.getHireDate());
        assertEquals(ContractType.CLT, model.getContractType());
        assertEquals(entity.getName(), model.getName().getValue());
        assertEquals(entity.getDocument(), model.getDocument().getValue());
        assertEquals(EntityType.NATURAL, model.getDocument().getType());
        assertEquals(entity.getEmail(), model.getEmail().getValue());
        assertEquals(entity.getPhone(), model.getPhone().getValue());
    }

    @Test
    void shouldMapToEntity() {
        Employee model = new Employee(
                1L,
                Money.fromInfra(new BigDecimal("5000.00")),
                LocalDate.now(),
                ContractType.CLT,
                EntityName.fromInfra("Funcionário"),
                EntityDocument.fromInfra("32585542009"),
                Email.fromInfra("employee@company.com"),
                Phone.fromInfra("99999999")
        );

        EmployeeJPA entity = employeeJPAMapper.toEntity(model);

        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getSalary().getValue(), entity.getSalary());
        assertEquals(model.getHireDate(), entity.getHireDate());
        assertEquals(model.getContractType(), entity.getContractType());
        assertEquals(model.getName().getValue(), entity.getName());
        assertEquals(model.getDocument().getValue(), entity.getDocument());
        assertEquals(model.getEmail().getValue(), entity.getEmail());
        assertEquals(model.getPhone().getValue(), entity.getPhone());
    }
}
