package com.example.project.infra.category.repository;

import com.example.project.infra.category.persistence.CategoryJPA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepositoryJPA extends JpaRepository<CategoryJPA, Long> {
}
