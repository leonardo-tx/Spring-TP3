package com.example.project.api.response.client.mapper;

import com.example.project.api.response.client.dto.ClientViewDTO;
import com.example.project.core.client.model.Client;
import com.example.project.core.generic.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ClientViewMapperTest {
    @InjectMocks
    private ClientViewMapper clientViewMapper;

    @Test
    void shouldParseSuccessfully() {
        Client client = new Client(
                1L,
                LocalDate.of(2000, 12, 16),
                EntityName.fromInfra("Clienteeee"),
                EntityDocument.fromInfra("95599407080"),
                Email.fromInfra("clienteeee@cliente.cliente"),
                Phone.fromInfra("123456789")
        );

        ClientViewDTO result = clientViewMapper.toEntity(client);

        assertNotNull(result);
        assertEquals(client.getId(), result.getId());
        assertEquals(client.getRegisterDate(), result.getRegisterDate());
        assertEquals(client.getName().getValue(), result.getName());
        assertEquals(client.getDocument().getValue(), result.getDocument());
        assertEquals(EntityType.NATURAL, result.getEntityType());
        assertEquals(client.getEmail().getValue(), result.getEmail());
        assertEquals(client.getPhone().getValue(), result.getPhone());
    }
}
