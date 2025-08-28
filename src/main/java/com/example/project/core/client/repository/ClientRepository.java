package com.example.project.core.client.repository;

import com.example.project.core.client.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    List<Client> findAll();
    Optional<Client> findById(Long id);
    void delete(Client client);
    Client save(Client client);
}
