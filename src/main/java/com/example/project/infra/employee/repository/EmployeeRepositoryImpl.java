package com.example.project.infra.employee.repository;

import com.example.project.core.employee.model.Employee;
import com.example.project.core.employee.repository.EmployeeRepository;
import com.example.project.core.generic.mapper.Mapper;
import com.example.project.infra.employee.persistence.EmployeeJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final EmployeeRepositoryJPA employeeRepositoryJPA;
    private final Mapper<Employee, EmployeeJPA> employeeJPAMapper;

    @Override
    public List<Employee> findAll() {
        return employeeRepositoryJPA.findAll()
                .stream()
                .map(employeeJPAMapper::toModel)
                .toList();
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return employeeRepositoryJPA.findById(id).map(employeeJPAMapper::toModel);
    }

    @Override
    public boolean existsById(Long id) {
        return employeeRepositoryJPA.existsById(id);
    }

    @Override
    public void delete(Employee employee) {
        employeeRepositoryJPA.deleteById(employee.getId());
    }

    @Override
    public Employee save(Employee employee) {
        EmployeeJPA employeeJPA = employeeJPAMapper.toEntity(employee);
        EmployeeJPA createdEmployeeJPA = employeeRepositoryJPA.save(employeeJPA);

        return employeeJPAMapper.toModel(createdEmployeeJPA);
    }
}
