package com.example.project.api.request.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientUpdateDTO {
    private Long id;
    private LocalDate registerDate;
    private String name;
    private String document;
    private String email;
    private String phone;
}
