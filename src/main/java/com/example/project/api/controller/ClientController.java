package com.example.project.api.controller;

import com.example.project.api.request.client.dto.ClientCreateDTO;
import com.example.project.api.request.client.dto.ClientUpdateDTO;
import com.example.project.api.response.ApiResponse;
import com.example.project.api.response.client.dto.ClientViewDTO;
import com.example.project.core.client.model.Client;
import com.example.project.core.client.service.ClientService;
import com.example.project.core.generic.mapper.InputMapper;
import com.example.project.core.generic.mapper.OutputMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;
    private final InputMapper<Client, ClientCreateDTO> clientCreateMapper;
    private final InputMapper<Client, ClientUpdateDTO> clientUpdateMapper;
    private final OutputMapper<Client, ClientViewDTO> clientViewMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClientViewDTO>>> getAll() {
        List<ClientViewDTO> clientViewDTOs = clientService.getAll()
                .stream()
                .map(clientViewMapper::toEntity)
                .toList();
        return ApiResponse.success(clientViewDTOs).createResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientViewDTO>> getById(@PathVariable("id") Long id) {
        Client client = clientService.getById(id);
        ClientViewDTO clientViewDTO = clientViewMapper.toEntity(client);

        return ApiResponse.success(clientViewDTO).createResponse(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ClientViewDTO>> create(@RequestBody ClientCreateDTO form) {
        Client client = clientCreateMapper.toModel(form);
        Client createdClient = clientService.create(client);
        ClientViewDTO clientViewDTO = clientViewMapper.toEntity(createdClient);

        return ApiResponse.success(clientViewDTO).createResponse(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientViewDTO>> updateById(@PathVariable("id") Long id, @RequestBody ClientCreateDTO form) {
        ClientUpdateDTO updateDTO = new ClientUpdateDTO(
                id,
                form.getRegisterDate(),
                form.getName(),
                form.getDocument(),
                form.getEmail(),
                form.getPhone()
        );
        Client changedClient = clientUpdateMapper.toModel(updateDTO);
        Client updatedClient = clientService.update(changedClient);
        ClientViewDTO clientViewDTO = clientViewMapper.toEntity(updatedClient);

        return ApiResponse.success(clientViewDTO).createResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteById(@PathVariable("id") Long id) {
        Client client = clientService.getById(id);
        clientService.delete(client);

        return ApiResponse.success(null).createResponse(HttpStatus.OK);
    }
}
