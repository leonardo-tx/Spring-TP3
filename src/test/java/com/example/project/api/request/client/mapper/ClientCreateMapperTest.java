package com.example.project.api.request.client.mapper;

import com.example.project.api.request.client.dto.ClientCreateDTO;
import com.example.project.core.client.factory.ClientParams;
import com.example.project.core.client.model.Client;
import com.example.project.core.client.factory.ClientFactory;
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
class ClientCreateMapperTest {
    @Mock
    private ClientFactory clientFactory;
    
    @InjectMocks
    private ClientCreateMapper clientCreateMapper;

    @Test
    void shouldMapSuccessfully() {
        ClientCreateDTO createDTO = new ClientCreateDTO(LocalDate.now(), "Client", "15128099008", "client@mail.com", "999999999");

        when(clientFactory.create(any(ClientParams.class))).thenAnswer(invocation -> {
            ClientParams params = invocation.getArgument(0);
            assertNull(params.getId());
            assertEquals(createDTO.getRegisterDate(), params.getRegisterDate());
            assertEquals(createDTO.getName(), params.getName());
            assertEquals(createDTO.getDocument(), params.getDocument());
            assertEquals(createDTO.getEmail(), params.getEmail());
            assertEquals(createDTO.getPhone(), params.getPhone());

            return mock(Client.class);
        });

        Client result = clientCreateMapper.toModel(createDTO);

        assertNotNull(result);
        verify(clientFactory, times(1)).create(any(ClientParams.class));
    }
}
