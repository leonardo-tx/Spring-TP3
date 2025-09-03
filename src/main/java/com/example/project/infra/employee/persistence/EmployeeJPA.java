package com.example.project.infra.employee.persistence;

import com.example.project.core.employee.model.ContractType;
import com.example.project.core.generic.model.*;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "Employee")
@Table(name = "TB_EMPLOYEE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "salary", nullable = false, precision = Money.MAX_PRECISION, scale = Money.SCALE)
    private BigDecimal salary;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "contract_type", nullable = false, length = 16)
    private ContractType contractType;

    @Column(name = "name", nullable = false, length = EntityName.MAX_LENGTH)
    private String name;

    @Column(name = "document", nullable = false, length = EntityDocument.CNPJ_LENGTH)
    private String document;

    @Column(name = "email", nullable = false, length = Email.MAX_LENGTH)
    private String email;

    @Column(name = "phone", length = Phone.MAX_LENGTH)
    private String phone;
}
