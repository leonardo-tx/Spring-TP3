package com.example.project.infra.employee.mapper;

import com.example.project.core.employee.model.Employee;
import com.example.project.core.generic.mapper.Mapper;
import com.example.project.infra.employee.persistence.EmployeeJPA;
import org.springframework.stereotype.Component;

@Component
public class EmployeeJPAMapper implements Mapper<Employee, EmployeeJPA> {
    @Override
    public Employee toModel(EmployeeJPA entity) {
        return null;
    }

    @Override
    public EmployeeJPA toEntity(Employee model) {
        return null;
    }
}
