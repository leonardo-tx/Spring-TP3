package com.example.project.api.response.employee.mapper;

import com.example.project.api.response.employee.dto.EmployeeViewDTO;
import com.example.project.core.employee.model.Employee;
import com.example.project.core.generic.mapper.OutputMapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeViewMapper implements OutputMapper<Employee, EmployeeViewDTO> {
    @Override
    public EmployeeViewDTO toEntity(Employee model) {
        return null;
    }
}
