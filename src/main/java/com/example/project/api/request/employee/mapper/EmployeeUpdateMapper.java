package com.example.project.api.request.employee.mapper;

import com.example.project.api.request.employee.dto.EmployeeUpdateDTO;
import com.example.project.core.employee.factory.EmployeeFactory;
import com.example.project.core.employee.factory.EmployeeParams;
import com.example.project.core.employee.model.Employee;
import com.example.project.core.generic.mapper.InputMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeUpdateMapper implements InputMapper<Employee, EmployeeUpdateDTO> {
    private final EmployeeFactory employeeFactory;

    @Override
    public Employee toModel(EmployeeUpdateDTO entity) {
        EmployeeParams params = new EmployeeParams(
                entity.getId(),
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
