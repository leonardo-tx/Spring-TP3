package com.example.project.core.client.factory;

import com.example.project.core.client.model.Client;
import com.example.project.core.generic.model.Email;
import com.example.project.core.generic.model.EntityDocument;
import com.example.project.core.generic.model.EntityName;
import com.example.project.core.generic.model.Phone;
import org.springframework.stereotype.Component;

@Component
public class ClientFactoryImpl implements ClientFactory {
    @Override
    public Client create(ClientParams params) {
        EntityName name = EntityName.valueOf(params.getName());
        EntityDocument document = EntityDocument.valueOf(params.getDocument());
        Email email = Email.valueOf(params.getEmail());
        Phone phone = Phone.valueOf(params.getPhone());

        return new Client(
                params.getId(),
                params.getRegisterDate(),
                name,
                document,
                email,
                phone
        );
    }
}
