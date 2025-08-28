package com.example.project.core.generic.mapper;

public interface OutputMapper<M, E> {
    E toEntity(M model);
}
