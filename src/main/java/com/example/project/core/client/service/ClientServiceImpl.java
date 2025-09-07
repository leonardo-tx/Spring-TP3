package com.example.project.core.client.service;

import com.example.project.core.client.model.Client;
import com.example.project.core.client.repository.ClientRepository;
import com.example.project.core.generic.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        Objects.requireNonNull(client);
        if (client.getId() == null) {
            throw new IllegalArgumentException("The client to delete must have an identifier.");
        }
        clientRepository.delete(client);
    }

    @Override
    public Client create(Client client) {
        Objects.requireNonNull(client);
        if (client.getId() != null) {
            throw new IllegalArgumentException("The client to create must not have an identifier.");
        }
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        Objects.requireNonNull(client);
        if (client.getId() == null) {
            throw new IllegalArgumentException("The client to update must have an identifier.");
        }
        if (!clientRepository.existsById(client.getId())) {
            throw new NotFoundException("client.not.found", "Could not find the client with the specified id.");
        }
        return clientRepository.save(client);
    }
}
