package com.example.project.api.handler;

import com.example.project.api.response.ApiResponse;
import com.example.project.core.generic.exception.NotFoundException;
import com.example.project.core.generic.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class RestExceptionHandlerTest {
    @InjectMocks
    private RestExceptionHandler restExceptionHandler;

    @Test
    void shouldReturnBadRequestWithValidationException() {
        ValidationException validationException = new ValidationException("test.code", "test message");
        ResponseEntity<ApiResponse<Object>> response = restExceptionHandler.validationException(validationException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getError());
        assertEquals(validationException.getCode(), response.getBody().getError().getCode());
        assertEquals(validationException.getMessage(), response.getBody().getError().getMessage());
    }

    @Test
    void shouldReturnNotFoundWithNotFoundException() {
        NotFoundException notFoundException = new NotFoundException("test.code", "test message");
        ResponseEntity<ApiResponse<Object>> response = restExceptionHandler.notFoundException(notFoundException);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getError());
        assertEquals(notFoundException.getCode(), response.getBody().getError().getCode());
        assertEquals(notFoundException.getMessage(), response.getBody().getError().getMessage());
    }
}
