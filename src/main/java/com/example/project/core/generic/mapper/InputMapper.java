package com.example.project.core.generic.mapper;

public interface InputMapper<M, E> {
    M toModel(E entity);
}
