package com.example.shitty_demo.core.mappers;

import java.sql.SQLException;

@FunctionalInterface
public interface MapperTo<To, From> {
    To mapTo(From param) throws Exception;
}
