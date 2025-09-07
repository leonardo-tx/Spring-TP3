package com.example.project.api.request.client.mapper;

import com.example.project.api.request.client.dto.ClientUpdateDTO;
import com.example.project.core.client.factory.ClientFactory;
import com.example.project.core.client.factory.ClientParams;
import com.example.project.core.client.model.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ClientUpdateMapperTest {
    @Mock
    private ClientFactory clientFactory;

    @InjectMocks
    private ClientUpdateMapper clientUpdateMapper;

    @Test
    void shouldMapSuccessfully() {
        ClientUpdateDTO updateDTO = new ClientUpdateDTO(5L, LocalDate.now(), "Client", "15128099008", "client@mail.com", "999999999");

        when(clientFactory.create(any(ClientParams.class))).thenAnswer(invocation -> {
            ClientParams params = invocation.getArgument(0);
            assertEquals(updateDTO.getId(), params.getId());
            assertEquals(updateDTO.getRegisterDate(), params.getRegisterDate());
            assertEquals(updateDTO.getName(), params.getName());
            assertEquals(updateDTO.getDocument(), params.getDocument());
            assertEquals(updateDTO.getEmail(), params.getEmail());
            assertEquals(updateDTO.getPhone(), params.getPhone());

            return mock(Client.class);
        });

        Client result = clientUpdateMapper.toModel(updateDTO);

        assertNotNull(result);
        verify(clientFactory, times(1)).create(any(ClientParams.class));
    }
}
