package com.example.project.core.client.service;

import com.example.project.core.client.model.Client;
import com.example.project.core.client.repository.ClientRepository;
import com.example.project.core.generic.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client getById(Long id) {
        return clientRepository.findById(id).orElseThrow(() ->
                new NotFoundException("client.not.found", "Could not find the client with the specified id.")
        );
    }

    @Override
    public void delete(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("The client must not be null.");
        }
        if (client.getId() == null) {
            throw new IllegalArgumentException("The client to delete must have an identifier.");
        }
        clientRepository.delete(client);
    }

    @Override
    public Client create(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("The client must not be null.");
        }
        if (client.getId() != null) {
            throw new IllegalArgumentException("The client to create must not have an identifier.");
        }
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client partialClient, Client targetClient) {
        // TODO
        return null;
    }
}
