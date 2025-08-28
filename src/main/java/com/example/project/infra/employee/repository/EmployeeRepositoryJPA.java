package com.example.project.infra.employee.repository;

import com.example.project.infra.employee.persistence.EmployeeJPA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepositoryJPA extends JpaRepository<EmployeeJPA, Long> {
}
