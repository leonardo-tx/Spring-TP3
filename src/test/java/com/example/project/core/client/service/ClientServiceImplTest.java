package com.example.project.core.client.service;

import com.example.project.core.client.model.Client;
import com.example.project.core.client.repository.ClientRepository;
import com.example.project.core.generic.exception.CoreException;
import com.example.project.core.generic.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    void shouldReturnOnGetAll() {
        List<Client> clients = List.of(mock(Client.class), mock(Client.class));
        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> returnedClients = clientService.getAll();

        assertEquals(2, returnedClients.size());
        assertSame(clients, returnedClients);

        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnOnGetById() {
        Client client = mock(Client.class);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        assertSame(client, clientService.getById(1L));

        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowWhenNotFoundOnGetById() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        CoreException exception = assertThrows(NotFoundException.class, () -> clientService.getById(1L));

        assertEquals("client.not.found", exception.getCode());
        assertEquals("Could not find the client with the specified id.", exception.getMessage());

        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void shouldDeleteClient() {
        Client client = mock(Client.class);
        when(client.getId()).thenReturn(2L);

        assertDoesNotThrow(() -> clientService.delete(client));

        verify(clientRepository, times(1)).delete(client);
    }

    @Test
    void shouldNotDeleteClientWithoutId() {
        Client client = mock(Client.class);
        when(client.getId()).thenReturn(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> clientService.delete(client)
        );

        assertEquals("The client to delete must have an identifier.", exception.getMessage());
        verifyNoInteractions(clientRepository);
    }

    @Test
    void shouldCreateClient() {
        Client client = mock(Client.class);
        when(client.getId()).thenReturn(null);
        when(clientRepository.save(client)).thenReturn(client);

        Client createdClient = assertDoesNotThrow(() -> clientService.create(client));

        assertSame(client, createdClient);

        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void shouldNotCreateClientWithId() {
        Client client = mock(Client.class);
        when(client.getId()).thenReturn(3L);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> clientService.create(client)
        );

        assertEquals("The client to create must not have an identifier.", exception.getMessage());
        verifyNoInteractions(clientRepository);
    }

    @Test
    void shouldUpdateClient() {
        Client client = mock(Client.class);
        when(client.getId()).thenReturn(4L);
        when(clientRepository.existsById(4L)).thenReturn(true);
        when(clientRepository.save(client)).thenReturn(client);

        Client updatedClient = assertDoesNotThrow(() -> clientService.update(client));

        assertSame(client, updatedClient);

        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void shouldNotUpdateClientWithoutId() {
        Client client = mock(Client.class);
        when(client.getId()).thenReturn(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> clientService.update(client)
        );

        assertEquals("The client to update must have an identifier.", exception.getMessage());
        verifyNoInteractions(clientRepository);
    }

    @Test
    void shouldNotUpdateIfNotFound() {
        Client client = mock(Client.class);
        when(client.getId()).thenReturn(4L);
        when(clientRepository.existsById(4L)).thenReturn(false);

        CoreException exception = assertThrows(NotFoundException.class, () -> clientService.update(client));

        assertEquals("client.not.found", exception.getCode());
        assertEquals("Could not find the client with the specified id.", exception.getMessage());

        verifyNoMoreInteractions(clientRepository);
    }
}
