package com.example.project.api.request.employee.mapper;

import com.example.project.api.request.employee.dto.EmployeeUpdateDTO;
import com.example.project.core.employee.factory.EmployeeFactory;
import com.example.project.core.employee.factory.EmployeeParams;
import com.example.project.core.employee.model.ContractType;
import com.example.project.core.employee.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class EmployeeUpdateMapperTest {
    @Mock
    private EmployeeFactory employeeFactory;

    @InjectMocks
    private EmployeeUpdateMapper employeeUpdateMapper;

    @Test
    void shouldMapSuccessfully() {
        EmployeeUpdateDTO updateDTO = new EmployeeUpdateDTO(11L, new BigDecimal("4200.00"), LocalDate.now(), ContractType.CLT, "Employee", "63916152092", "employee@mail.com", "3131313131");

        when(employeeFactory.create(any(EmployeeParams.class))).thenAnswer(invocation -> {
            EmployeeParams params = invocation.getArgument(0);
            assertEquals(updateDTO.getId(), params.getId());
            assertEquals(updateDTO.getSalary(), params.getSalary());
            assertEquals(updateDTO.getHireDate(), params.getHireDate());
            assertEquals(updateDTO.getContractType(), params.getContractType());
            assertEquals(updateDTO.getName(), params.getName());
            assertEquals(updateDTO.getDocument(), params.getDocument());
            assertEquals(updateDTO.getEmail(), params.getEmail());
            assertEquals(updateDTO.getPhone(), params.getPhone());

            return mock(Employee.class);
        });

        Employee result = employeeUpdateMapper.toModel(updateDTO);

        assertNotNull(result);
        verify(employeeFactory, times(1)).create(any(EmployeeParams.class));
    }
}
