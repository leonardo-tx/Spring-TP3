package com.example.project.api.request.client.mapper;

import com.example.project.api.request.client.dto.ClientCreateDTO;
import com.example.project.core.client.model.Client;
import com.example.project.core.generic.mapper.InputMapper;
import org.springframework.stereotype.Component;

@Component
public class ClientCreateMapper implements InputMapper<Client, ClientCreateDTO> {
    @Override
    public Client toModel(ClientCreateDTO entity) {
        return null;
    }
}
