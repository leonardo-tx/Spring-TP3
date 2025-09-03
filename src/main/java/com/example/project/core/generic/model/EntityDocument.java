package com.example.project.core.generic.model;

import com.example.project.core.generic.exception.ValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class EntityDocument {
    public static final int CPF_LENGTH = 11;
    public static final int CNPJ_LENGTH = 14;

    private final String value;
    private final EntityType type;

    public static EntityDocument valueOf(String value) {
        if (value == null) {
            throw new ValidationException("entity.document.null", "The document value cannot be null.");
        }
        String cleanValue = value.replaceAll("\\D", "");
        EntityType type = switch (cleanValue.length()) {
            case CPF_LENGTH -> EntityType.NATURAL;
            case CNPJ_LENGTH -> EntityType.LEGAL;
            default -> {
                String message = String.format("The CPF must have exactly %d digits or the CNPJ must have %d digits.", CPF_LENGTH, CNPJ_LENGTH);
                throw new ValidationException("entity.document.invalid.length", message);
            }
        };
        String normalizedValue = switch (type) {
            case NATURAL -> validateCPF(cleanValue);
            case LEGAL -> validateCNPJ(cleanValue);
        };
        return new EntityDocument(normalizedValue, type);
    }

    public static EntityDocument fromInfra(String value) {
        if (value.length() == CPF_LENGTH) {
            return new EntityDocument(value, EntityType.NATURAL);
        }
        if (value.length() == CNPJ_LENGTH) {
            return new EntityDocument(value, EntityType.LEGAL);
        }
        throw new IllegalArgumentException("The document from the infrastructure is not valid.");
    }

    private static String validateCPF(String value) {
        String cleanValue = value.replaceAll("\\D", "");
        if (isAllSameDigits(cleanValue)) {
            throw new ValidationException("entity.document.cpf.invalid.sequence", "The CPF cannot have all identical digits.");
        }
        int digit1 = calculateCPFDigit(cleanValue, 10);
        if (digit1 != Character.getNumericValue(cleanValue.charAt(CPF_LENGTH - 2))) {
            throw new ValidationException("entity.document.cpf.invalid.digit1", "The CPF first verification digit doesn't match.");
        }
        int digit2 = calculateCPFDigit(cleanValue, 11);
        if (digit2 != Character.getNumericValue(cleanValue.charAt(CPF_LENGTH - 1))) {
            throw new ValidationException("entity.document.cpf.invalid.digit2", "The CPF second verification digit doesn't match.");
        }
        return cleanValue;
    }

    private static String validateCNPJ(String value) {
        String cleanValue = value.replaceAll("\\D", "");
        if (isAllSameDigits(cleanValue)) {
            throw new ValidationException("entity.document.cnpj.invalid.sequence", "The CNPJ cannot have all identical digits.");
        }
        int digit1 = calculateCNPJDigit(cleanValue, 5);
        if (digit1 != Character.getNumericValue(cleanValue.charAt(CNPJ_LENGTH - 2))) {
            throw new ValidationException("entity.document.cnpj.invalid.digit1", "The CNPJ first verification digit doesn't match.");
        }
        int digit2 = calculateCNPJDigit(cleanValue, 6);
        if (digit2 != Character.getNumericValue(cleanValue.charAt(CNPJ_LENGTH - 1))) {
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
