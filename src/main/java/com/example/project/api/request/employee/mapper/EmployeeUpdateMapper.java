package com.example.project.api.request.employee.mapper;

import com.example.project.api.request.employee.dto.EmployeeUpdateDTO;
import com.example.project.core.employee.model.Employee;
import com.example.project.core.generic.mapper.InputMapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeUpdateMapper implements InputMapper<Employee, EmployeeUpdateDTO> {
    @Override
    public Employee toModel(EmployeeUpdateDTO entity) {
        return null;
    }
}
