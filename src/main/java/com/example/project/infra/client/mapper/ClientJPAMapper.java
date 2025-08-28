package com.example.project.infra.client.mapper;

import com.example.project.core.client.model.Client;
import com.example.project.core.generic.mapper.Mapper;
import com.example.project.infra.client.persistence.ClientJPA;
import org.springframework.stereotype.Component;

@Component
public class ClientJPAMapper implements Mapper<Client, ClientJPA> {
    @Override
    public Client toModel(ClientJPA entity) {
        return null;
    }

    @Override
    public ClientJPA toEntity(Client model) {
        return null;
    }
}
