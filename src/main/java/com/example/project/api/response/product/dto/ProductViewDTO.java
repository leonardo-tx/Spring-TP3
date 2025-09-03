package com.example.project.api.response.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductViewDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
}
