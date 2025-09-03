package com.example.project.core.employee.model;

import com.example.project.core.generic.exception.ValidationException;
import com.example.project.core.generic.model.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public final class Employee extends Entity {
    private final Long id;
    private final Money salary;
    private final LocalDate hireDate;
    private final ContractType contractType;

    public Employee(Long id, Money salary, LocalDate hireDate, ContractType contractType, EntityName name, EntityDocument document, Email email, Phone phone) {
        super(name, document, email, phone);
        if (hireDate == null) {
            throw new ValidationException("employee.hire.date.null", "The employee hire date cannot be null.");
        }
        if (contractType == null) {
            throw new ValidationException("employee.contract.type.null", "The employee contract type cannot be null.");
        }
        this.id = id;
        this.salary = Objects.requireNonNull(salary);
        this.hireDate = Objects.requireNonNull(hireDate);
        this.contractType = contractType;
    }
}
