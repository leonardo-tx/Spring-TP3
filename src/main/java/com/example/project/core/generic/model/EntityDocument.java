package com.example.project.core.generic.model;

import com.example.project.core.generic.exception.ValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class EntityDocument {
    private final String value;
    private final EntityType type;

    public static EntityDocument valueOf(String value, EntityType type) {
        if (type == null) {
            throw new ValidationException("entity.document.type.null", "The document type cannot be null.");
        }
        if (value == null) {
            throw new ValidationException("entity.document.value.null", "The document value cannot be null.");
        }
        String normalizedValue = switch (type) {
            case NATURAL -> validateCPF(value);
            case LEGAL -> validateCNPJ(value);
        };
        return new EntityDocument(normalizedValue, type);
    }

    public static EntityDocument fromInfra(String value, EntityType type) {
        return new EntityDocument(value, type);
    }

    private static String validateCPF(String value) {
        String cleanValue = value.replaceAll("\\D", "");
        if (cleanValue.length() != 11) {
            throw new ValidationException("entity.document.cpf.invalid.length", "The CPF must have exactly 11 digits.");
        }
        if (isAllSameDigits(cleanValue)) {
            throw new ValidationException("entity.document.cpf.invalid.sequence", "The CPF cannot have all identical digits.");
        }
        int digit1 = calculateCPFDigit(cleanValue, 10);
        if (digit1 != Character.getNumericValue(cleanValue.charAt(9))) {
            throw new ValidationException("entity.document.cpf.invalid.digit1", "The CPF first verification digit doesn't match.");
        }
        int digit2 = calculateCPFDigit(cleanValue, 11);
        if (digit2 != Character.getNumericValue(cleanValue.charAt(10))) {
            throw new ValidationException("entity.document.cpf.invalid.digit2", "The CPF second verification digit doesn't match.");
        }
        return cleanValue;
    }

    private static String validateCNPJ(String value) {
        String cleanValue = value.replaceAll("\\D", "");
        if (cleanValue.length() != 14) {
            throw new ValidationException("entity.document.cnpj.invalid.length", "The CNPJ must have exactly 14 digits.");
        }
        if (isAllSameDigits(cleanValue)) {
            throw new ValidationException("entity.document.cnpj.invalid.sequence", "The CNPJ cannot have all identical digits.");
        }
        int digit1 = calculateCNPJDigit(cleanValue, 5);
        if (digit1 != Character.getNumericValue(cleanValue.charAt(12))) {
            throw new ValidationException("entity.document.cnpj.invalid.digit1", "The CNPJ first verification digit doesn't match.");
        }
        int digit2 = calculateCNPJDigit(cleanValue, 6);
        if (digit2 != Character.getNumericValue(cleanValue.charAt(13))) {
            throw new ValidationException("entity.document.cnpj.invalid.digit2", "The CNPJ second verification digit doesn't match.");
        }

        return cleanValue;
    }

    private static boolean isAllSameDigits(String value) {
        char firstChar = value.charAt(0);
        for (int i = 1; i < value.length(); i++) {
            if (value.charAt(i) != firstChar) {
                return false;
            }
        }
        return true;
    }

    private static int calculateCPFDigit(String cpf, int weightStart) {
        int sum = 0;
        for (int i = 0; i < weightStart - 1; i++) {
            int digit = Character.getNumericValue(cpf.charAt(i));
            sum += digit * (weightStart - i);
        }
        int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }

    private static int calculateCNPJDigit(String cnpj, int weightStart) {
        int[] weights = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int sum = 0;
        for (int i = 0; i < weightStart - 1; i++) {
            int digit = Character.getNumericValue(cnpj.charAt(i));
            sum += digit * weights[i];
        }
        int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }
}
