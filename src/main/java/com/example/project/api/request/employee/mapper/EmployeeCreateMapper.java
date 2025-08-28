package com.example.project.api.request.employee.mapper;

import com.example.project.api.request.employee.dto.EmployeeCreateDTO;
import com.example.project.core.employee.model.Employee;
import com.example.project.core.generic.mapper.InputMapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeCreateMapper implements InputMapper<Employee, EmployeeCreateDTO> {
    @Override
    public Employee toModel(EmployeeCreateDTO entity) {
        return null;
    }
}
