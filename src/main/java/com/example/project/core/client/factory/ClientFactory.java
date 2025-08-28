package com.example.project.core.client.factory;

import com.example.project.core.client.model.Client;

public interface ClientFactory {
    Client create(ClientParams params);
}
