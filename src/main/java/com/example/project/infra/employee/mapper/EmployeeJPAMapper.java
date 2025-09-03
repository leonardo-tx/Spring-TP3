package com.example.project.infra.employee.mapper;

import com.example.project.core.employee.model.Employee;
import com.example.project.core.generic.mapper.Mapper;
import com.example.project.core.generic.model.*;
import com.example.project.infra.employee.persistence.EmployeeJPA;
import org.springframework.stereotype.Component;

@Component
public class EmployeeJPAMapper implements Mapper<Employee, EmployeeJPA> {
    @Override
    public Employee toModel(EmployeeJPA entity) {
        return new Employee(
                entity.getId(),
                Money.fromInfra(entity.getSalary()),
                entity.getHireDate(),
                entity.getContractType(),
                EntityName.fromInfra(entity.getName()),
                EntityDocument.fromInfra(entity.getDocument()),
                Email.fromInfra(entity.getEmail()),
                Phone.fromInfra(entity.getPhone())
        );
    }

    @Override
    public EmployeeJPA toEntity(Employee model) {
        return EmployeeJPA.builder()
                .id(model.getId())
                .salary(model.getSalary().getValue())
                .hireDate(model.getHireDate())
                .contractType(model.getContractType())
                .name(model.getName().getValue())
                .document(model.getDocument().getValue())
                .email(model.getEmail().getValue())
                .phone(model.getPhone().getValue())
                .build();
    }
}
