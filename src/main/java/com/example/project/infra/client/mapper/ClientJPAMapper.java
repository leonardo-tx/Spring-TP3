package com.example.project.infra.client.mapper;

import com.example.project.core.client.model.Client;
import com.example.project.core.generic.mapper.Mapper;
import com.example.project.core.generic.model.Email;
import com.example.project.core.generic.model.EntityDocument;
import com.example.project.core.generic.model.EntityName;
import com.example.project.core.generic.model.Phone;
import com.example.project.infra.client.persistence.ClientJPA;
import org.springframework.stereotype.Component;

@Component
public class ClientJPAMapper implements Mapper<Client, ClientJPA> {
    @Override
    public Client toModel(ClientJPA entity) {
        return new Client(
                entity.getId(),
                entity.getRegisterDate(),
                EntityName.fromInfra(entity.getName()),
                EntityDocument.fromInfra(entity.getDocument()),
                Email.fromInfra(entity.getEmail()),
                Phone.fromInfra(entity.getPhone())
        );
    }

    @Override
    public ClientJPA toEntity(Client model) {
        return ClientJPA.builder()
                .id(model.getId())
                .registerDate(model.getRegisterDate())
                .name(model.getName().getValue())
                .document(model.getDocument().getValue())
                .email(model.getEmail().getValue())
                .phone(model.getPhone().getValue())
                .build();
    }
}
