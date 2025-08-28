package com.example.project.api.request.client.mapper;

import com.example.project.api.request.client.dto.ClientUpdateDTO;
import com.example.project.core.client.model.Client;
import com.example.project.core.generic.mapper.InputMapper;
import org.springframework.stereotype.Component;

@Component
public class ClientUpdateMapper implements InputMapper<Client, ClientUpdateDTO> {
    @Override
    public Client toModel(ClientUpdateDTO entity) {
        return null;
    }
}
