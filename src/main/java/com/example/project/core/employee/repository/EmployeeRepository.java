package com.example.project.core.employee.repository;

import com.example.project.core.employee.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    List<Employee> findAll();
    Optional<Employee> findById(Long id);
    void delete(Employee employee);
    Employee save(Employee employee);
}
