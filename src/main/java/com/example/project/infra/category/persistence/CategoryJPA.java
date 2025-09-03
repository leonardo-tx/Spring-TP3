package com.example.project.infra.category.persistence;

import com.example.project.core.category.model.CategoryName;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Category")
@Table(name = "TB_CATEGORY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = CategoryName.MAX_LENGTH)
    private String name;
}
