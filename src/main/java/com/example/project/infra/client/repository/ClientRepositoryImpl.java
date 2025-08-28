package com.example.project.infra.client.repository;

import com.example.project.core.client.model.Client;
import com.example.project.core.client.repository.ClientRepository;
import com.example.project.core.generic.mapper.Mapper;
import com.example.project.infra.client.persistence.ClientJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientRepository {
    private final ClientRepositoryJPA clientRepositoryJPA;
    private final Mapper<Client, ClientJPA> clientJPAMapper;

    @Override
    public List<Client> findAll() {
        return clientRepositoryJPA.findAll()
                .stream()
                .map(clientJPAMapper::toModel)
                .toList();
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientRepositoryJPA.findById(id).map(clientJPAMapper::toModel);
    }

    @Override
    public void delete(Client client) {
        clientRepositoryJPA.deleteById(client.getId());
    }

    @Override
    public Client save(Client client) {
        ClientJPA clientJPA = clientJPAMapper.toEntity(client);
        ClientJPA createdClientJPA = clientRepositoryJPA.save(clientJPA);

        return clientJPAMapper.toModel(createdClientJPA);
    }
}
