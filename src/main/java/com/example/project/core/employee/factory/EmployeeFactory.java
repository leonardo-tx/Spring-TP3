package com.example.project.core.employee.factory;

import com.example.project.core.employee.model.Employee;

public interface EmployeeFactory {
    Employee create(EmployeeParams params);
}
