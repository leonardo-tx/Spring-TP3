package com.example.project.infra.supplier.persistence;

import com.example.project.core.generic.model.Email;
import com.example.project.core.generic.model.EntityDocument;
import com.example.project.core.generic.model.EntityName;
import com.example.project.core.generic.model.Phone;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Supplier")
@Table(name = "TB_SUPPLIER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = EntityName.MAX_LENGTH)
    private String name;

    @Column(name = "document", nullable = false, length = EntityDocument.CNPJ_LENGTH)
    private String document;

    @Column(name = "email", nullable = false, length = Email.MAX_LENGTH)
    private String email;

    @Column(name = "phone", length = Phone.MAX_LENGTH)
    private String phone;
}
