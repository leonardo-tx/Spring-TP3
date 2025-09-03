package com.example.project.api.request.employee.mapper;

import com.example.project.api.request.employee.dto.EmployeeCreateDTO;
import com.example.project.core.employee.factory.EmployeeFactory;
import com.example.project.core.employee.factory.EmployeeParams;
import com.example.project.core.employee.model.Employee;
import com.example.project.core.generic.mapper.InputMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeCreateMapper implements InputMapper<Employee, EmployeeCreateDTO> {
    private final EmployeeFactory employeeFactory;

    @Override
    public Employee toModel(EmployeeCreateDTO entity) {
        EmployeeParams params = new EmployeeParams(
                null,
                entity.getSalary(),
                entity.getHireDate(),
                entity.getContractType(),
                entity.getName(),
                entity.getDocument(),
                entity.getEmail(),
                entity.getPhone()
        );
        return employeeFactory.create(params);
    }
}
