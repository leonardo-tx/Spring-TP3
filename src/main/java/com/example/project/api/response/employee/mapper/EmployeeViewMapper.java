package com.example.project.api.response.employee.mapper;

import com.example.project.api.response.employee.dto.EmployeeViewDTO;
import com.example.project.core.employee.model.Employee;
import com.example.project.core.generic.mapper.OutputMapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeViewMapper implements OutputMapper<Employee, EmployeeViewDTO> {
    @Override
    public EmployeeViewDTO toEntity(Employee model) {
        return new EmployeeViewDTO(
                model.getId(),
                model.getSalary().getValue(),
                model.getHireDate(),
                model.getContractType(),
                model.getName().getValue(),
                model.getDocument().getValue(),
                model.getDocument().getType(),
                model.getEmail().getValue(),
                model.getPhone().getValue()
        );
    }
}
