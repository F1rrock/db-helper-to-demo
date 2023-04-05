package com.example.shitty_demo.infrastructure.gateways;

import com.example.shitty_demo.core.errors.exceptions.ServerException;
import com.example.shitty_demo.core.mappers.MapperTo;
import com.example.shitty_demo.core.requests.Request;
import com.example.shitty_demo.infrastructure.models.Human;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class HumanGateway {
    private final Request<Human, ResultSet> dbHelper;
    private final MapperTo<Human, ResultSet> mapper;

    public HumanGateway(Request<Human, ResultSet> dbHelper, MapperTo<Human, ResultSet> mapper) {
        this.dbHelper = dbHelper;
        this.mapper = mapper;
    }

    public Stream<Human> getHumans() {
        try {
            return dbHelper.get(
                    "SELECT * FROM human",
                    mapper,
                    null
            );
        } catch (ServerException ignored) {
            System.out.println("something went wrong");
        }
        return null;
    }

    public Stream<Human> addHuman(Human param) {
        try {
            dbHelper.set(
                    "INSERT INTO HUMAN (name, age) VALUES (?, ?)",
                    new ArrayList<>(
                            List.of(
                                    param.name(),
                                    param.age()
                            )
                    )
            );
            return getHumans();
        } catch (ServerException e) {
            System.out.println("something went wrong");
        }
        return null;
    }

    public Stream<Human> updateHuman(Human oldParam, Human newParam) {
        try {
            dbHelper.set(
                    "UPDATE human SET name = ?, age = ? WHERE name = ?",
                    new ArrayList<>(
                            List.of(
                                    newParam.name(),
                                    newParam.age(),
                                    oldParam.name()
                            )
                    )
            );
            return getHumans();
        } catch (ServerException e) {
            System.out.println("something went wrong");
        }
        return null;
    }

    public Stream<Human> delete(Human param) {
        try {
            dbHelper.set(
                    "DELETE FROM human WHERE name = ?",
                    new ArrayList<>(
                            List.of(
                                    param.name()
                            )
                    )
            );
            return getHumans();
        } catch (ServerException e) {
            System.out.println("something went wrong");
        }
        return null;
    }
}
