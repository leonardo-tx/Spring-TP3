package com.example.project.api.request.client.mapper;

import com.example.project.api.request.client.dto.ClientCreateDTO;
import com.example.project.core.client.factory.ClientFactory;
import com.example.project.core.client.factory.ClientParams;
import com.example.project.core.client.model.Client;
import com.example.project.core.generic.mapper.InputMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientCreateMapper implements InputMapper<Client, ClientCreateDTO> {
    private final ClientFactory clientFactory;

    @Override
    public Client toModel(ClientCreateDTO entity) {
        ClientParams params = new ClientParams(
                null,
                entity.getRegisterDate(),
                entity.getName(),
                entity.getDocument(),
                entity.getEmail(),
                entity.getPhone()
        );
        return clientFactory.create(params);
    }
}
