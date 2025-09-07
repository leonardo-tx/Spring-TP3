package com.example.project.api.controller;

import com.example.project.api.request.supplier.dto.SupplierCreateDTO;
import com.example.project.api.request.supplier.dto.SupplierUpdateDTO;
import com.example.project.api.response.supplier.dto.SupplierViewDTO;
import com.example.project.core.generic.mapper.InputMapper;
import com.example.project.core.generic.mapper.OutputMapper;
import com.example.project.core.generic.model.EntityType;
import com.example.project.core.supplier.model.Supplier;
import com.example.project.core.supplier.service.SupplierService;
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

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(SupplierController.class)
class SupplierControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockitoBean
    private SupplierService supplierService;

    @MockitoBean
    private InputMapper<Supplier, SupplierCreateDTO> supplierCreateMapper;

    @MockitoBean
    private InputMapper<Supplier, SupplierUpdateDTO> supplierUpdateMapper;

    @MockitoBean
    private OutputMapper<Supplier, SupplierViewDTO> supplierViewMapper;

    @Test
    void shouldReturnAll() throws Exception {
        List<Supplier> suppliers = List.of(
                mock(Supplier.class),
                mock(Supplier.class)
        );
        List<SupplierViewDTO> supplierViewDTOs = List.of(
                new SupplierViewDTO(8L, "Supplier1", "12345678901", EntityType.LEGAL, "supplier1@email.com", "11999999999"),
                new SupplierViewDTO(1L, "Supplier2", "98765432100", EntityType.LEGAL, "supplier2@email.com", "11988888888")
        );

        when(supplierService.getAll()).thenReturn(suppliers);
        for (int i = 0; i < suppliers.size(); i++) {
            when(supplierViewMapper.toEntity(suppliers.get(i))).thenReturn(supplierViewDTOs.get(i));
        }

        ResultActions resultActions = mockMvc.perform(get("/supplier"));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result", hasSize(2)));

        for (int i = 0; i < supplierViewDTOs.size(); i++) {
            SupplierViewDTO supplier = supplierViewDTOs.get(i);
            String init = "result[" + i + "]";
            resultActions.andExpect(jsonPath(init + ".id").value(supplier.getId()))
                    .andExpect(jsonPath(init + ".name").value(supplier.getName()))
                    .andExpect(jsonPath(init + ".document").value(supplier.getDocument()))
                    .andExpect(jsonPath(init + ".entityType").value(supplier.getEntityType().toString()))
                    .andExpect(jsonPath(init + ".email").value(supplier.getEmail()))
                    .andExpect(jsonPath(init + ".phone").value(supplier.getPhone()));
        }
    }

    @Test
    void shouldReturnEmptyListWhenNoSuppliersExist() throws Exception {
        when(supplierService.getAll()).thenReturn(List.of());

        ResultActions resultActions = mockMvc.perform(get("/supplier"));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result", hasSize(0)));
    }

    @Test
    void shouldReturnSupplierById() throws Exception {
        Long supplierId = 1L;
        Supplier supplier = mock(Supplier.class);
        SupplierViewDTO supplierViewDTO = new SupplierViewDTO(supplierId, "Supplier1", "12345678901", EntityType.LEGAL, "supplier1@email.com", "11999999999");

        when(supplierService.getById(supplierId)).thenReturn(supplier);
        when(supplierViewMapper.toEntity(supplier)).thenReturn(supplierViewDTO);

        ResultActions resultActions = mockMvc.perform(get("/supplier/{id}", supplierId));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result.id").value(supplierId))
                .andExpect(jsonPath("result.name").value(supplierViewDTO.getName()))
                .andExpect(jsonPath("result.document").value(supplierViewDTO.getDocument()))
                .andExpect(jsonPath("result.entityType").value(supplierViewDTO.getEntityType().toString()))
                .andExpect(jsonPath("result.email").value(supplierViewDTO.getEmail()))
                .andExpect(jsonPath("result.phone").value(supplierViewDTO.getPhone()));
    }

    @Test
    void shouldCreateSupplier() throws Exception {
        SupplierCreateDTO createDTO = new SupplierCreateDTO("New Supplier", "12345678901", "newsupplier@email.com", "11999999999");
        Supplier supplierToCreate = mock(Supplier.class);
        Supplier createdSupplier = mock(Supplier.class);
        SupplierViewDTO supplierViewDTO = new SupplierViewDTO(1L, "New Supplier", "12345678901", EntityType.LEGAL, "newsupplier@email.com", "11999999999");

        when(supplierCreateMapper.toModel(createDTO)).thenReturn(supplierToCreate);
        when(supplierService.create(supplierToCreate)).thenReturn(createdSupplier);
        when(supplierViewMapper.toEntity(createdSupplier)).thenReturn(supplierViewDTO);

        ResultActions resultActions = mockMvc.perform(post("/supplier")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.CREATED)
                .andExpect(jsonPath("result.id").value(1L))
                .andExpect(jsonPath("result.name").value(supplierViewDTO.getName()))
                .andExpect(jsonPath("result.document").value(supplierViewDTO.getDocument()))
                .andExpect(jsonPath("result.entityType").value(supplierViewDTO.getEntityType().toString()))
                .andExpect(jsonPath("result.email").value(supplierViewDTO.getEmail()))
                .andExpect(jsonPath("result.phone").value(supplierViewDTO.getPhone()));
    }

    @Test
    void shouldUpdateSupplier() throws Exception {
        Long supplierId = 1L;
        SupplierCreateDTO createDTO = new SupplierCreateDTO("Updated Supplier", "12345678901", "updated@email.com", "11999999999");
        Supplier supplierToUpdate = mock(Supplier.class);
        Supplier updatedSupplier = mock(Supplier.class);
        SupplierViewDTO supplierViewDTO = new SupplierViewDTO(supplierId, "Updated Supplier", "12345678901", EntityType.LEGAL, "updated@email.com", "11999999999");

        when(supplierUpdateMapper.toModel(any(SupplierUpdateDTO.class))).thenReturn(supplierToUpdate);
        when(supplierService.update(supplierToUpdate)).thenReturn(updatedSupplier);
        when(supplierViewMapper.toEntity(updatedSupplier)).thenReturn(supplierViewDTO);

        ResultActions resultActions = mockMvc.perform(put("/supplier/{id}", supplierId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result.id").value(supplierId))
                .andExpect(jsonPath("result.name").value(supplierViewDTO.getName()))
                .andExpect(jsonPath("result.document").value(supplierViewDTO.getDocument()))
                .andExpect(jsonPath("result.entityType").value(supplierViewDTO.getEntityType().toString()))
                .andExpect(jsonPath("result.email").value(supplierViewDTO.getEmail()))
                .andExpect(jsonPath("result.phone").value(supplierViewDTO.getPhone()));
    }

    @Test
    void shouldDeleteSupplier() throws Exception {
        Long supplierId = 1L;
        Supplier supplier = mock(Supplier.class);

        when(supplierService.getById(supplierId)).thenReturn(supplier);

        ResultActions resultActions = mockMvc.perform(delete("/supplier/{id}", supplierId));
        MockMvcHelper.testSuccessfulResponse(resultActions, HttpStatus.OK)
                .andExpect(jsonPath("result").value(nullValue()));

        verify(supplierService, times(1)).delete(supplier);
    }
}
