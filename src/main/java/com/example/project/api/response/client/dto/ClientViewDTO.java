package com.example.project.api.response.client.dto;

import com.example.project.core.generic.model.EntityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientViewDTO {
    private Long id;
    private LocalDate registerDate;
    private String name;
    private String document;
    private EntityType entityType;
    private String email;
    private String phone;
}
