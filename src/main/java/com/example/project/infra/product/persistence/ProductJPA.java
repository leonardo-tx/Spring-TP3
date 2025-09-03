package com.example.project.infra.product.persistence;

import com.example.project.core.generic.model.Money;
import com.example.project.core.product.model.ProductDescription;
import com.example.project.core.product.model.ProductName;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "Product")
@Table(name = "TB_PRODUCT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = ProductName.MAX_LENGTH)
    private String name;

    @Column(name = "description", nullable = false, length = ProductDescription.MAX_LENGTH)
    private String description;

    @Column(name = "price", nullable = false, precision = Money.MAX_PRECISION, scale = Money.SCALE)
    private BigDecimal price;
}
