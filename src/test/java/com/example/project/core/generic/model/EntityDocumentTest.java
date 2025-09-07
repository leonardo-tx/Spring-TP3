package com.example.project.core.generic.model;

import com.example.project.core.generic.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EntityDocumentTest {
    @ParameterizedTest
    @ValueSource(strings = {
            "80188829083",
            "38206706020",
            "96729646098",
            "21502549050",
            "87251206030"
    })
    void shouldCreateEntityDocumentWithValidCPF(String value) {
        EntityDocument document = EntityDocument.valueOf(value);

        assertEquals(value, document.getValue());
        assertEquals(EntityType.NATURAL, document.getType());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "78303314000150",
            "05408836000143",
            "44560842000173",
            "90317554000100",
            "37969531000105"
    })
    void shouldCreateEntityDocumentWithValidCNPJ(String value) {
        EntityDocument document = EntityDocument.valueOf(value);

        assertEquals(value, document.getValue());
        assertEquals(EntityType.LEGAL, document.getType());
    }

    @Test
    void shouldThrowExceptionWhenValueIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, () -> EntityDocument.valueOf(null));

        assertEquals("entity.document.null", exception.getCode());
        assertEquals("The document value cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"023", "8234567890", "343456789012", "123456789012345"})
    void shouldThrowExceptionWhenLengthIsInvalid(String invalidValue) {
        ValidationException exception = assertThrows(ValidationException.class, () -> EntityDocument.valueOf(invalidValue));

        String expectedMessage = String.format(
                "The CPF must have exactly %d digits or the CNPJ must have %d digits.",
                EntityDocument.CPF_LENGTH,
                EntityDocument.CNPJ_LENGTH
        );
        assertEquals("entity.document.invalid.length", exception.getCode());
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"11111111111", "22222222222", "00000000000"})
    void shouldThrowExceptionWhenCPFHasAllSameDigits(String invalidCPF) {
        ValidationException exception = assertThrows(ValidationException.class, () -> EntityDocument.valueOf(invalidCPF));

        assertEquals("entity.document.cpf.invalid.sequence", exception.getCode());
        assertEquals("The CPF cannot have all identical digits.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"11111111111111", "22222222222222", "00000000000000"})
    void shouldThrowExceptionWhenCNPJHasAllSameDigits(String invalidCNPJ) {
        ValidationException exception = assertThrows(ValidationException.class, () -> EntityDocument.valueOf(invalidCNPJ));

        assertEquals("entity.document.cnpj.invalid.sequence", exception.getCode());
        assertEquals("The CNPJ cannot have all identical digits.", exception.getMessage());
    }
}
