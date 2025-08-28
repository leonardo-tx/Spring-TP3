package com.example.project.infra.category.repository;

import com.example.project.core.category.model.Category;
import com.example.project.core.category.repository.CategoryRepository;
import com.example.project.core.generic.mapper.Mapper;
import com.example.project.infra.category.persistence.CategoryJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {
    private final CategoryRepositoryJPA categoryRepositoryJPA;
    private final Mapper<Category, CategoryJPA> categoryJPAMapper;

    @Override
    public List<Category> findAll() {
        return categoryRepositoryJPA.findAll()
                .stream()
                .map(categoryJPAMapper::toModel)
                .toList();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepositoryJPA.findById(id).map(categoryJPAMapper::toModel);
    }

    @Override
    public void delete(Category category) {
        categoryRepositoryJPA.deleteById(category.getId());
    }

    @Override
    public Category save(Category category) {
        CategoryJPA categoryJPA = categoryJPAMapper.toEntity(category);
        CategoryJPA createdCategoryJPA = categoryRepositoryJPA.save(categoryJPA);

        return categoryJPAMapper.toModel(createdCategoryJPA);
    }
}
