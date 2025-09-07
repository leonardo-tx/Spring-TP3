package com.example.project.api.controller;

import com.example.project.ProjectApplication;
import com.example.project.api.request.client.dto.ClientCreateDTO;
import com.example.project.api.request.client.dto.ClientUpdateDTO;
import com.example.project.api.response.client.dto.ClientViewDTO;
import com.example.project.core.client.model.Client;
import com.example.project.core.client.service.ClientService;
import com.example.project.core.generic.mapper.InputMapper;
import com.example.project.core.generic.mapper.OutputMapper;
import com.example.project.core.generic.model.EntityType;
import com.example.project.test.util.MockMvcHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ClientController.class)
class ClientControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockitoBean
    private ClientService clientService;

    @MockitoBean
    private InputMapper<Client, ClientCreateDTO> clientCreateMapper;

    @MockitoBean
    private InputMapper<Client, ClientUpdateDTO> clientUpdateMapper;

    @MockitoBean
    private OutputMapper<Client, ClientViewDTO> clientViewMapper;

    @Test
    void shouldReturnAll() throws Exception {
        List<Client> clients = List.of(
                mock(Client.class),
                mock(Client.class)
        );
        List<ClientViewDTO> clientViewDTOs = List.of(
                new ClientViewDTO(8L, LocalDate.now(), "Client1", "12345678901", EntityType.NATURAL, "client1@email.com", "11999999999"),
                new ClientViewDTO(1L, LocalDate.now(), "Client2", "98765432100", EntityType.NATURAL, "client2@email.com", "11988888888")
        );

        when(clientService.getAll()).thenReturn(clients);
        for (int i = 0; i < clients.size(); i++) {
            when(clientViewMapper.toEntity(clients.get(i))).thenReturn(clientViewDTOs.get(i));
        }

        ResultActions resultActions = mockMvc.perform(get("/client"));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result", hasSize(2)));

        for (int i = 0; i < clientViewDTOs.size(); i++) {
            ClientViewDTO client = clientViewDTOs.get(i);
            String init = "result[" + i + "]";
            resultActions.andExpect(jsonPath(init + ".id").value(client.getId()))
                    .andExpect(jsonPath(init + ".registerDate").value(client.getRegisterDate().toString()))
                    .andExpect(jsonPath(init + ".name").value(client.getName()))
                    .andExpect(jsonPath(init + ".document").value(client.getDocument()))
                    .andExpect(jsonPath(init + ".entityType").value(client.getEntityType().toString()))
                    .andExpect(jsonPath(init + ".email").value(client.getEmail()))
                    .andExpect(jsonPath(init + ".phone").value(client.getPhone()));
        }
    }

    @Test
    void shouldReturnEmptyListWhenNoClientsExist() throws Exception {
        when(clientService.getAll()).thenReturn(List.of());

        ResultActions resultActions = mockMvc.perform(get("/client"));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result", hasSize(0)));
    }

    @Test
    void shouldReturnClientById() throws Exception {
        Long clientId = 1L;
        Client client = mock(Client.class);
        ClientViewDTO clientViewDTO = new ClientViewDTO(clientId, LocalDate.now(), "Client1", "12345678901", EntityType.NATURAL, "client1@email.com", "11999999999");

        when(clientService.getById(clientId)).thenReturn(client);
        when(clientViewMapper.toEntity(client)).thenReturn(clientViewDTO);

        ResultActions resultActions = mockMvc.perform(get("/client/{id}", clientId));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result.id").value(clientId))
                .andExpect(jsonPath("result.registerDate").value(clientViewDTO.getRegisterDate().toString()))
                .andExpect(jsonPath("result.name").value(clientViewDTO.getName()))
                .andExpect(jsonPath("result.document").value(clientViewDTO.getDocument()))
                .andExpect(jsonPath("result.entityType").value(clientViewDTO.getEntityType().toString()))
                .andExpect(jsonPath("result.email").value(clientViewDTO.getEmail()))
                .andExpect(jsonPath("result.phone").value(clientViewDTO.getPhone()));
    }

    @Test
    void shouldCreateClient() throws Exception {
        ClientCreateDTO createDTO = new ClientCreateDTO(LocalDate.now(), "Nova Categoria", "12345678901", "novo@email.com", "11999999999");
        Client clientToCreate = mock(Client.class);
        Client createdClient = mock(Client.class);
        ClientViewDTO clientViewDTO = new ClientViewDTO(1L, LocalDate.now(), "Client1", "12345678901", EntityType.NATURAL, "client1@email.com", "11999999999");

        when(clientCreateMapper.toModel(createDTO)).thenReturn(clientToCreate);
        when(clientService.create(clientToCreate)).thenReturn(createdClient);
        when(clientViewMapper.toEntity(createdClient)).thenReturn(clientViewDTO);

        ResultActions resultActions = mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.CREATED)
                .andExpect(jsonPath("result.id").value(1L))
                .andExpect(jsonPath("result.registerDate").value(clientViewDTO.getRegisterDate().toString()))
                .andExpect(jsonPath("result.name").value(clientViewDTO.getName()))
                .andExpect(jsonPath("result.document").value(clientViewDTO.getDocument()))
                .andExpect(jsonPath("result.entityType").value(clientViewDTO.getEntityType().toString()))
                .andExpect(jsonPath("result.email").value(clientViewDTO.getEmail()))
                .andExpect(jsonPath("result.phone").value(clientViewDTO.getPhone()));
    }

    @Test
    void shouldUpdateClient() throws Exception {
        Long clientId = 1L;
        ClientCreateDTO createDTO = new ClientCreateDTO(LocalDate.now(), "Categoria Atualizada", "12345678901", "atualizado@email.com", "11999999999");
        Client clientToUpdate = mock(Client.class);
        Client updatedClient = mock(Client.class);
        ClientViewDTO clientViewDTO = new ClientViewDTO(clientId, LocalDate.now(), "Categoria Atualizada", "12345678901", EntityType.NATURAL, "atualizado@email.com", "11999999999");

        when(clientUpdateMapper.toModel(any(ClientUpdateDTO.class))).thenReturn(clientToUpdate);
        when(clientService.update(clientToUpdate)).thenReturn(updatedClient);
        when(clientViewMapper.toEntity(updatedClient)).thenReturn(clientViewDTO);

        ResultActions resultActions = mockMvc.perform(put("/client/{id}", clientId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result.registerDate").value(clientViewDTO.getRegisterDate().toString()))
                .andExpect(jsonPath("result.name").value(clientViewDTO.getName()))
                .andExpect(jsonPath("result.document").value(clientViewDTO.getDocument()))
                .andExpect(jsonPath("result.entityType").value(clientViewDTO.getEntityType().toString()))
                .andExpect(jsonPath("result.email").value(clientViewDTO.getEmail()))
                .andExpect(jsonPath("result.phone").value(clientViewDTO.getPhone()));
    }

    @Test
    void shouldDeleteClient() throws Exception {
        Long clientId = 1L;
        Client client = mock(Client.class);

        when(clientService.getById(clientId)).thenReturn(client);

        ResultActions resultActions = mockMvc.perform(delete("/client/{id}", clientId));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result").value(nullValue()));

        verify(clientService, times(1)).delete(client);
    }
}
