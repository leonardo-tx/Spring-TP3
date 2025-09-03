package com.example.project.core.client.service;

import com.example.project.core.client.model.Client;

import java.util.List;

public interface ClientService {
    List<Client> getAll();
    Client getById(Long id);
    void delete(Client client);
    Client create(Client client);
    Client update(Client client);
}
