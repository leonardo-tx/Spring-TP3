package com.example.project.infra.client.repository;

import com.example.project.core.client.model.Client;
import com.example.project.core.generic.mapper.Mapper;
import com.example.project.infra.client.persistence.ClientJPA;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientRepositoryImplTest {
    @Mock
    private ClientRepositoryJPA clientRepositoryJPA;

    @Mock
    private Mapper<Client, ClientJPA> clientJPAMapper;

    @InjectMocks
    private ClientRepositoryImpl clientRepository;

    @Test
    void shouldFindAll() {
        List<ClientJPA> entities = List.of(mock(ClientJPA.class), mock(ClientJPA.class));
        when(clientRepositoryJPA.findAll()).thenReturn(entities);

        List<Client> categories = assertDoesNotThrow(() -> clientRepository.findAll());

        assertEquals(entities.size(), categories.size());
        verify(clientJPAMapper, times(entities.size())).toModel(any(ClientJPA.class));
    }

    @Test
    void shouldFindById() {
        ClientJPA mockClientJPA = mock(ClientJPA.class);
        Client mockClient = mock(Client.class);

        when(clientRepositoryJPA.findById(3L)).thenReturn(Optional.of(mockClientJPA));
        when(clientJPAMapper.toModel(mockClientJPA)).thenReturn(mockClient);

        Optional<Client> optionalClient = assertDoesNotThrow(() -> clientRepository.findById(3L));

        assertTrue(optionalClient.isPresent());
        assertSame(mockClient, optionalClient.get());

        verify(clientJPAMapper, times(1)).toModel(mockClientJPA);
    }

    @Test
    void shouldReturnOptionEmptyWhenNotFindById() {
        when(clientRepositoryJPA.findById(3L)).thenReturn(Optional.empty());

        Optional<Client> optionalClient = assertDoesNotThrow(() -> clientRepository.findById(3L));

        assertTrue(optionalClient.isEmpty());

        verifyNoInteractions(clientJPAMapper);
    }

    @Test
    void shouldDelete() {
        Client mockClient = mock(Client.class);
        when(mockClient.getId()).thenReturn(4L);

        assertDoesNotThrow(() -> clientRepository.delete(mockClient));

        verify(clientRepositoryJPA).deleteById(4L);
    }

    @Test
    void shouldSave() {
        Client mockClient = mock(Client.class);
        Client mockSavedClient = mock(Client.class);
        ClientJPA mockClientJPA = mock(ClientJPA.class);
        ClientJPA mockSavedClientJPA = mock(ClientJPA.class);

        when(clientJPAMapper.toEntity(mockClient)).thenReturn(mockClientJPA);
        when(clientRepositoryJPA.save(mockClientJPA)).thenReturn(mockSavedClientJPA);
        when(clientJPAMapper.toModel(mockSavedClientJPA)).thenReturn(mockSavedClient);

        Client returnedClient = assertDoesNotThrow(() -> clientRepository.save(mockClient));

        assertSame(mockSavedClient, returnedClient);
        verify(clientJPAMapper, times(1)).toEntity(mockClient);
        verify(clientJPAMapper, times(1)).toModel(mockSavedClientJPA);
        verify(clientRepositoryJPA).save(mockClientJPA);
    }

    @Test
    void shouldReturnIfExists() {
        when(clientRepositoryJPA.existsById(4L)).thenReturn(true);
        assertTrue(clientRepository.existsById(4L));

        when(clientRepositoryJPA.existsById(2L)).thenReturn(false);
        assertFalse(clientRepository.existsById(2L));
    }
}
