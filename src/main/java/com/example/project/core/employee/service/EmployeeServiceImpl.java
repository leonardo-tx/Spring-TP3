package com.example.project.core.employee.service;

import com.example.project.core.employee.model.Employee;
import com.example.project.core.employee.repository.EmployeeRepository;
import com.example.project.core.generic.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() ->
                new NotFoundException("employee.not.found", "Could not find the employee with the specified id.")
        );
    }

    @Override
    public void delete(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("The employee must not be null.");
        }
        if (employee.getId() == null) {
            throw new IllegalArgumentException("The employee to delete must have an identifier.");
        }
        employeeRepository.delete(employee);
    }

    @Override
    public Employee create(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("The employee must not be null.");
        }
        if (employee.getId() != null) {
            throw new IllegalArgumentException("The employee to create must not have an identifier.");
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Employee partialEmployee, Employee targetEmployee) {
        // TODO
        return null;
    }
}
