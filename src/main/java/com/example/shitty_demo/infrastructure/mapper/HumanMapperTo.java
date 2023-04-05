package com.example.shitty_demo.infrastructure.mapper;

import com.example.shitty_demo.core.mappers.MapperTo;
import com.example.shitty_demo.infrastructure.models.Human;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class HumanMapperTo implements MapperTo<Human, ResultSet> {
    @Override
    public Human mapTo(ResultSet param) throws Exception {
        return new Human(
                param.getString("name"),
                param.getInt("age")
        );
    }
}
