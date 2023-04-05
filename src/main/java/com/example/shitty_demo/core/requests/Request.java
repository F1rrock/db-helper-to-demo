package com.example.shitty_demo.core.requests;

import com.example.shitty_demo.core.errors.exceptions.ServerException;
import com.example.shitty_demo.core.mappers.MapperTo;

import java.util.List;
import java.util.stream.Stream;

public interface Request<Model extends Record, Type> {
    Stream<Model> get(String query, MapperTo<Model, Type> mapper, List<?> attributes) throws ServerException;
    void set(String query, List<?> attributes) throws ServerException;
}
