package com.example.project.api.controller;

import com.example.project.api.request.employee.dto.EmployeeCreateDTO;
import com.example.project.api.request.employee.dto.EmployeeUpdateDTO;
import com.example.project.api.response.employee.dto.EmployeeViewDTO;
import com.example.project.core.employee.model.ContractType;
import com.example.project.core.employee.model.Employee;
import com.example.project.core.employee.service.EmployeeService;
import com.example.project.core.generic.mapper.InputMapper;
import com.example.project.core.generic.mapper.OutputMapper;
import com.example.project.core.generic.model.EntityType;
import com.example.project.test.util.MockMvcHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockitoBean
    private EmployeeService employeeService;

    @MockitoBean
    private InputMapper<Employee, EmployeeCreateDTO> employeeCreateMapper;

    @MockitoBean
    private InputMapper<Employee, EmployeeUpdateDTO> employeeUpdateMapper;

    @MockitoBean
    private OutputMapper<Employee, EmployeeViewDTO> employeeViewMapper;

    @Test
    void shouldReturnAll() throws Exception {
        List<Employee> employees = List.of(
                mock(Employee.class),
                mock(Employee.class)
        );
        List<EmployeeViewDTO> employeeViewDTOs = List.of(
                new EmployeeViewDTO(8L, new BigDecimal("5000.00"), LocalDate.now(), ContractType.CLT, "Employee1", "12345678901", EntityType.NATURAL, "employee1@email.com", "11999999999"),
                new EmployeeViewDTO(1L, new BigDecimal("6000.00"), LocalDate.now(), ContractType.PJ, "Employee2", "98765432100", EntityType.NATURAL, "employee2@email.com", "11988888888")
        );

        when(employeeService.getAll()).thenReturn(employees);
        for (int i = 0; i < employees.size(); i++) {
            when(employeeViewMapper.toEntity(employees.get(i))).thenReturn(employeeViewDTOs.get(i));
        }

        ResultActions resultActions = mockMvc.perform(get("/employee"));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result", hasSize(2)));

        for (int i = 0; i < employeeViewDTOs.size(); i++) {
            EmployeeViewDTO employee = employeeViewDTOs.get(i);
            String init = "result[" + i + "]";
            resultActions.andExpect(jsonPath(init + ".id").value(employee.getId()))
                    .andExpect(jsonPath(init + ".salary").value(employee.getSalary()))
                    .andExpect(jsonPath(init + ".hireDate").value(employee.getHireDate().toString()))
                    .andExpect(jsonPath(init + ".contractType").value(employee.getContractType().toString()))
                    .andExpect(jsonPath(init + ".name").value(employee.getName()))
                    .andExpect(jsonPath(init + ".document").value(employee.getDocument()))
                    .andExpect(jsonPath(init + ".entityType").value(employee.getEntityType().toString()))
                    .andExpect(jsonPath(init + ".email").value(employee.getEmail()))
                    .andExpect(jsonPath(init + ".phone").value(employee.getPhone()));
        }
    }

    @Test
    void shouldReturnEmptyListWhenNoEmployeesExist() throws Exception {
        when(employeeService.getAll()).thenReturn(List.of());

        ResultActions resultActions = mockMvc.perform(get("/employee"));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result", hasSize(0)));
    }

    @Test
    void shouldReturnEmployeeById() throws Exception {
        Long employeeId = 1L;
        Employee employee = mock(Employee.class);
        EmployeeViewDTO employeeViewDTO = new EmployeeViewDTO(employeeId, new BigDecimal("5000.00"), LocalDate.now(), ContractType.CLT, "Employee1", "12345678901", EntityType.NATURAL, "employee1@email.com", "11999999999");

        when(employeeService.getById(employeeId)).thenReturn(employee);
        when(employeeViewMapper.toEntity(employee)).thenReturn(employeeViewDTO);

        ResultActions resultActions = mockMvc.perform(get("/employee/{id}", employeeId));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result.id").value(employeeId))
                .andExpect(jsonPath("result.salary").value(employeeViewDTO.getSalary()))
                .andExpect(jsonPath("result.hireDate").value(employeeViewDTO.getHireDate().toString()))
                .andExpect(jsonPath("result.contractType").value(employeeViewDTO.getContractType().toString()))
                .andExpect(jsonPath("result.name").value(employeeViewDTO.getName()))
                .andExpect(jsonPath("result.document").value(employeeViewDTO.getDocument()))
                .andExpect(jsonPath("result.entityType").value(employeeViewDTO.getEntityType().toString()))
                .andExpect(jsonPath("result.email").value(employeeViewDTO.getEmail()))
                .andExpect(jsonPath("result.phone").value(employeeViewDTO.getPhone()));
    }

    @Test
    void shouldCreateEmployee() throws Exception {
        EmployeeCreateDTO createDTO = new EmployeeCreateDTO(new BigDecimal("5000.00"), LocalDate.now(), ContractType.CLT, "Employee1", "12345678901", "employee1@email.com", "11999999999");
        Employee employeeToCreate = mock(Employee.class);
        Employee createdEmployee = mock(Employee.class);
        EmployeeViewDTO employeeViewDTO = new EmployeeViewDTO(1L, new BigDecimal("5000.00"), LocalDate.now(), ContractType.CLT, "Employee1", "12345678901", EntityType.NATURAL, "employee1@email.com", "11999999999");

        when(employeeCreateMapper.toModel(createDTO)).thenReturn(employeeToCreate);
        when(employeeService.create(employeeToCreate)).thenReturn(createdEmployee);
        when(employeeViewMapper.toEntity(createdEmployee)).thenReturn(employeeViewDTO);

        ResultActions resultActions = mockMvc.perform(post("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.CREATED)
                .andExpect(jsonPath("result.id").value(1L))
                .andExpect(jsonPath("result.salary").value(employeeViewDTO.getSalary()))
                .andExpect(jsonPath("result.hireDate").value(employeeViewDTO.getHireDate().toString()))
                .andExpect(jsonPath("result.contractType").value(employeeViewDTO.getContractType().toString()))
                .andExpect(jsonPath("result.name").value(employeeViewDTO.getName()))
                .andExpect(jsonPath("result.document").value(employeeViewDTO.getDocument()))
                .andExpect(jsonPath("result.entityType").value(employeeViewDTO.getEntityType().toString()))
                .andExpect(jsonPath("result.email").value(employeeViewDTO.getEmail()))
                .andExpect(jsonPath("result.phone").value(employeeViewDTO.getPhone()));
    }

    @Test
    void shouldUpdateEmployee() throws Exception {
        Long employeeId = 1L;
        EmployeeCreateDTO createDTO = new EmployeeCreateDTO(new BigDecimal("6000.00"), LocalDate.now(), ContractType.PJ, "Employee Updated", "12345678901", "updated@email.com", "11999999999");
        Employee employeeToUpdate = mock(Employee.class);
        Employee updatedEmployee = mock(Employee.class);
        EmployeeViewDTO employeeViewDTO = new EmployeeViewDTO(employeeId, new BigDecimal("6000.00"), LocalDate.now(), ContractType.PJ, "Employee Updated", "12345678901", EntityType.NATURAL, "updated@email.com", "11999999999");

        when(employeeUpdateMapper.toModel(any(EmployeeUpdateDTO.class))).thenReturn(employeeToUpdate);
        when(employeeService.update(employeeToUpdate)).thenReturn(updatedEmployee);
        when(employeeViewMapper.toEntity(updatedEmployee)).thenReturn(employeeViewDTO);

        ResultActions resultActions = mockMvc.perform(put("/employee/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result.id").value(employeeId))
                .andExpect(jsonPath("result.salary").value(employeeViewDTO.getSalary()))
                .andExpect(jsonPath("result.hireDate").value(employeeViewDTO.getHireDate().toString()))
                .andExpect(jsonPath("result.contractType").value(employeeViewDTO.getContractType().toString()))
                .andExpect(jsonPath("result.name").value(employeeViewDTO.getName()))
                .andExpect(jsonPath("result.document").value(employeeViewDTO.getDocument()))
                .andExpect(jsonPath("result.entityType").value(employeeViewDTO.getEntityType().toString()))
                .andExpect(jsonPath("result.email").value(employeeViewDTO.getEmail()))
                .andExpect(jsonPath("result.phone").value(employeeViewDTO.getPhone()));
    }

    @Test
    void shouldDeleteEmployee() throws Exception {
        Long employeeId = 1L;
        Employee employee = mock(Employee.class);

        when(employeeService.getById(employeeId)).thenReturn(employee);

        ResultActions resultActions = mockMvc.perform(delete("/employee/{id}", employeeId));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result").value(nullValue()));

        verify(employeeService, times(1)).delete(employee);
    }
}
