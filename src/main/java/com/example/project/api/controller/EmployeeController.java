package com.example.project.api.controller;

import com.example.project.api.request.employee.dto.EmployeeCreateDTO;
import com.example.project.api.request.employee.dto.EmployeeUpdateDTO;
import com.example.project.api.response.ApiResponse;
import com.example.project.api.response.employee.dto.EmployeeViewDTO;
import com.example.project.core.employee.model.Employee;
import com.example.project.core.employee.service.EmployeeService;
import com.example.project.core.generic.mapper.InputMapper;
import com.example.project.core.generic.mapper.OutputMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final InputMapper<Employee, EmployeeCreateDTO> employeeCreateMapper;
    private final InputMapper<Employee, EmployeeUpdateDTO> employeeUpdateMapper;
    private final OutputMapper<Employee, EmployeeViewDTO> employeeViewMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeViewDTO>>> getAll() {
        List<EmployeeViewDTO> employeeViewDTOs = employeeService.getAll()
                .stream()
                .map(employeeViewMapper::toEntity)
                .toList();
        return ApiResponse.success(employeeViewDTOs).createResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeViewDTO>> getById(@PathVariable("id") Long id) {
        Employee employee = employeeService.getById(id);
        EmployeeViewDTO employeeViewDTO = employeeViewMapper.toEntity(employee);

        return ApiResponse.success(employeeViewDTO).createResponse(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeViewDTO>> create(@RequestBody EmployeeCreateDTO form) {
        Employee employee = employeeCreateMapper.toModel(form);
        Employee createdEmployee = employeeService.create(employee);
        EmployeeViewDTO employeeViewDTO = employeeViewMapper.toEntity(createdEmployee);

        return ApiResponse.success(employeeViewDTO).createResponse(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeViewDTO>> updateById(@PathVariable("id") Long id, @RequestBody EmployeeCreateDTO form) {
        EmployeeUpdateDTO updateDTO = new EmployeeUpdateDTO(
                id,
                form.getSalary(),
                form.getHireDate(),
                form.getContractType(),
                form.getName(),
                form.getDocument(),
                form.getEmail(),
                form.getPhone()
        );
        Employee changedEmployee = employeeUpdateMapper.toModel(updateDTO);
        Employee updatedEmployee = employeeService.update(changedEmployee);
        EmployeeViewDTO employeeViewDTO = employeeViewMapper.toEntity(updatedEmployee);

        return ApiResponse.success(employeeViewDTO).createResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteById(@PathVariable("id") Long id) {
        Employee employee = employeeService.getById(id);
        employeeService.delete(employee);

        return ApiResponse.success(null).createResponse(HttpStatus.OK);
    }
}
