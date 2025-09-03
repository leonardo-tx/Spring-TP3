package com.example.project.api.response.client.mapper;

import com.example.project.api.response.client.dto.ClientViewDTO;
import com.example.project.core.client.model.Client;
import com.example.project.core.generic.mapper.OutputMapper;
import org.springframework.stereotype.Component;

@Component
public class ClientViewMapper implements OutputMapper<Client, ClientViewDTO> {
    @Override
    public ClientViewDTO toEntity(Client model) {
        return new ClientViewDTO(
                model.getId(),
                model.getRegisterDate(),
                model.getName().getValue(),
                model.getDocument().getValue(),
                model.getDocument().getType(),
                model.getEmail().getValue(),
                model.getPhone().getValue()
        );
    }
}
