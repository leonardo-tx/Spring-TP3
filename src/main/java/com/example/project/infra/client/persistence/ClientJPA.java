package com.example.project.infra.client.persistence;

import com.example.project.core.generic.model.Email;
import com.example.project.core.generic.model.EntityDocument;
import com.example.project.core.generic.model.EntityName;
import com.example.project.core.generic.model.Phone;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "Client")
@Table(name = "TB_CLIENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "register_date", nullable = false)
    private LocalDate registerDate;

    @Column(name = "name", nullable = false, length = EntityName.MAX_LENGTH)
    private String name;

    @Column(name = "document", nullable = false, length = EntityDocument.CNPJ_LENGTH)
    private String document;

    @Column(name = "email", nullable = false, length = Email.MAX_LENGTH)
    private String email;

    @Column(name = "phone", nullable = false, length = Phone.MAX_LENGTH)
    private String phone;
}
