package com.example.project.core.generic.exception;

public class ValidationException extends CoreException {
    public ValidationException(String code, String message) {
        super(code, message);
    }
}
