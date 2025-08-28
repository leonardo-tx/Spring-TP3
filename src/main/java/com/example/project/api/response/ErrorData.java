package com.example.project.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class ErrorData {
    private final String code;
    private final String message;
}
