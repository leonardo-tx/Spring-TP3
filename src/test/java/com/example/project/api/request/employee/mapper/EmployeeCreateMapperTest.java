package com.example.project.api.request.employee.mapper;

import com.example.project.api.request.employee.dto.EmployeeCreateDTO;
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
class EmployeeCreateMapperTest {
    @Mock
    private EmployeeFactory employeeFactory;

    @InjectMocks
    private EmployeeCreateMapper employeeCreateMapper;

    @Test
    void shouldMapSuccessfully() {
        EmployeeCreateDTO createDTO = new EmployeeCreateDTO(new BigDecimal("4200.00"), LocalDate.now(), ContractType.CLT, "Employee", "63916152092", "employee@mail.com", "3131313131");

        when(employeeFactory.create(any(EmployeeParams.class))).thenAnswer(invocation -> {
            EmployeeParams params = invocation.getArgument(0);
            assertNull(params.getId());
            assertEquals(createDTO.getSalary(), params.getSalary());
            assertEquals(createDTO.getHireDate(), params.getHireDate());
            assertEquals(createDTO.getContractType(), params.getContractType());
            assertEquals(createDTO.getName(), params.getName());
            assertEquals(createDTO.getDocument(), params.getDocument());
            assertEquals(createDTO.getEmail(), params.getEmail());
            assertEquals(createDTO.getPhone(), params.getPhone());

            return mock(Employee.class);
        });

        Employee result = employeeCreateMapper.toModel(createDTO);

        assertNotNull(result);
        verify(employeeFactory, times(1)).create(any(EmployeeParams.class));
    }
}
