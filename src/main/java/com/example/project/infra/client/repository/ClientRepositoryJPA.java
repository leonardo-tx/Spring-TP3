package com.example.project.infra.client.repository;

import com.example.project.infra.client.persistence.ClientJPA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepositoryJPA extends JpaRepository<ClientJPA, Long> {
}
