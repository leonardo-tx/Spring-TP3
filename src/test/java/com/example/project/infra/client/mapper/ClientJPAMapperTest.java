package com.example.project.infra.client.mapper;

import com.example.project.core.client.model.Client;
import com.example.project.core.generic.model.*;
import com.example.project.infra.client.persistence.ClientJPA;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ClientJPAMapperTest {
    @InjectMocks
    private ClientJPAMapper clientJPAMapper;

    @Test
    void shouldMapToModel() {
        ClientJPA entity = ClientJPA.builder()
                .id(1L)
                .registerDate(LocalDate.now())
                .name("Cliente")
                .document("92913676000194")
                .email("client@client.com.br")
                .phone("9999")
                .build();
        Client model = clientJPAMapper.toModel(entity);

        assertEquals(entity.getId(), model.getId());
        assertEquals(entity.getRegisterDate(), model.getRegisterDate());
        assertEquals(entity.getName(), model.getName().getValue());
        assertEquals(entity.getDocument(), model.getDocument().getValue());
        assertEquals(EntityType.LEGAL, model.getDocument().getType());
        assertEquals(entity.getEmail(), model.getEmail().getValue());
        assertEquals(entity.getPhone(), model.getPhone().getValue());
    }

    @Test
    void shouldMapToEntity() {
        Client model = new Client(
                1L,
                LocalDate.now(),
                EntityName.fromInfra("Cliente"),
                EntityDocument.fromInfra("92913676000194"),
                Email.fromInfra("client@client.com.br"),
                Phone.fromInfra("9999")
        );
        ClientJPA entity = clientJPAMapper.toEntity(model);

        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getRegisterDate(), entity.getRegisterDate());
        assertEquals(model.getName().getValue(), entity.getName());
        assertEquals(model.getDocument().getValue(), entity.getDocument());
        assertEquals(model.getEmail().getValue(), entity.getEmail());
        assertEquals(model.getPhone().getValue(), entity.getPhone());
    }
}
