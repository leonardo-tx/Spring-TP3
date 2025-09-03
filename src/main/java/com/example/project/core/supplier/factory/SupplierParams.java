package com.example.project.core.supplier.factory;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class SupplierParams {
    private final Long id;
    private final String name;
    private final String document;
    private final String email;
    private final String phone;
}
