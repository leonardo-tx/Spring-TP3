package com.example.project.core.employee.factory;

import com.example.project.core.employee.model.Employee;
import com.example.project.core.generic.model.*;
import org.springframework.stereotype.Component;

@Component
public class EmployeeFactoryImpl implements EmployeeFactory {
    @Override
    public Employee create(EmployeeParams params) {
        Money salary = Money.valueOf(params.getSalary());
        EntityName name = EntityName.valueOf(params.getName());
        EntityDocument document = EntityDocument.valueOf(params.getDocument());
        Email email = Email.valueOf(params.getEmail());
        Phone phone = Phone.valueOf(params.getPhone());

        return new Employee(
                params.getId(),
                salary,
                params.getHireDate(),
                params.getContractType(),
                name,
                document,
                email,
                phone
        );
    }
}
