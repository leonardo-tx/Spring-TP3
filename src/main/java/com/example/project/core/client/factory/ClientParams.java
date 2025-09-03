package com.example.project.core.client.factory;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public final class ClientParams {
    private final Long id;
    private final LocalDate registerDate;
    private final String name;
    private final String document;
    private final String email;
    private final String phone;
}
