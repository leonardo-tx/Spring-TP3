package com.example.project.infra.product.repository;

import com.example.project.core.product.model.Product;
import com.example.project.core.generic.mapper.Mapper;
import com.example.project.core.product.repository.ProductRepository;
import com.example.project.infra.product.persistence.ProductJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductRepositoryJPA productRepositoryJPA;
    private final Mapper<Product, ProductJPA> productJPAMapper;

    @Override
    public List<Product> findAll() {
        return productRepositoryJPA.findAll()
                .stream()
                .map(productJPAMapper::toModel)
                .toList();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepositoryJPA.findById(id).map(productJPAMapper::toModel);
    }

    @Override
    public boolean existsById(Long id) {
        return productRepositoryJPA.existsById(id);
    }

    @Override
    public void delete(Product product) {
        productRepositoryJPA.deleteById(product.getId());
    }

    @Override
    public Product save(Product product) {
        ProductJPA productJPA = productJPAMapper.toEntity(product);
        ProductJPA createdProductJPA = productRepositoryJPA.save(productJPA);

        return productJPAMapper.toModel(createdProductJPA);
    }
}
