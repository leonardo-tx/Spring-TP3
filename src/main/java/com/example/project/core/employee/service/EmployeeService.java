package com.example.project.core.employee.service;

import com.example.project.core.employee.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAll();
    Employee getById(Long id);
    void delete(Employee employee);
    Employee create(Employee employee);
    Employee update(Employee partialEmployee, Employee targetEmployee);
}
