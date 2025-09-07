package com.example.project.core.employee.factory;

import com.example.project.core.employee.model.ContractType;
import com.example.project.core.employee.model.Employee;
import com.example.project.core.generic.model.EntityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class EmployeeFactoryImplTest {
    @InjectMocks
    private EmployeeFactoryImpl employeeFactory;

    @Test
    void shouldCreate() {
        EmployeeParams params = new EmployeeParams(
                4L,
                new BigDecimal("4500.00"),
                LocalDate.now(),
                ContractType.CLT,
                "Roberto",
                "11513638041",
                "aaa@outlook.com",
                "999999"
        );
        Employee employee = employeeFactory.create(params);

        assertEquals(params.getId(), employee.getId());
        assertEquals(params.getSalary(), employee.getSalary().getValue());
        assertEquals(params.getHireDate(), employee.getHireDate());
        assertEquals(params.getContractType(), employee.getContractType());
        assertEquals(params.getName(), employee.getName().getValue());
        assertEquals(params.getDocument(), employee.getDocument().getValue());
        assertEquals(EntityType.NATURAL, employee.getDocument().getType());
        assertEquals(params.getEmail(), employee.getEmail().getValue());
        assertEquals(params.getPhone(), employee.getPhone().getValue());
    }
}
