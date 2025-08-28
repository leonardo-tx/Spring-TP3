package com.example.project.core.employee.model;

import com.example.project.core.generic.exception.ValidationException;
import com.example.project.core.generic.model.*;
import lombok.Getter;

import java.util.Objects;

@Getter
public final class Employee extends Entity {
    private final Long id;
    private final Money salary;
    private final RegisterDate hireDate;
    private final ContractType contractType;

    public Employee(Long id, Money salary, RegisterDate hireDate, ContractType contractType, EntityDocument document, Email email) {
        super(document, email);
        if (contractType == null) {
            throw new ValidationException("contract.type.null", "The contract type cannot be null.");
        }
        this.id = id;
        this.salary = Objects.requireNonNull(salary);
        this.hireDate = Objects.requireNonNull(hireDate);
        this.contractType = contractType;
    }
}
