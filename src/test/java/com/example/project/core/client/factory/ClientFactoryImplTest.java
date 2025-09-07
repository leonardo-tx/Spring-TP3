package com.example.project.core.client.factory;

import com.example.project.core.client.model.Client;
import com.example.project.core.generic.model.EntityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ClientFactoryImplTest {
    @InjectMocks
    private ClientFactoryImpl clientFactory;

    @Test
    void shouldCreate() {
        ClientParams params = new ClientParams(
                2L,
                LocalDate.now(),
                "Cliente",
                "68157520037",
                "123@gmail.com",
                "100"
        );
        Client client = clientFactory.create(params);

        assertEquals(params.getId(), client.getId());
        assertEquals(params.getRegisterDate(), client.getRegisterDate());
        assertEquals(params.getName(), client.getName().getValue());
        assertEquals(params.getDocument(), client.getDocument().getValue());
        assertEquals(EntityType.NATURAL, client.getDocument().getType());
        assertEquals(params.getEmail(), client.getEmail().getValue());
        assertEquals(params.getPhone(), client.getPhone().getValue());
    }
}
