package com.example.shitty_demo.helpers;

import com.example.shitty_demo.core.connectors.DBConnector;
import com.example.shitty_demo.core.requests.Request;
import com.example.shitty_demo.core.errors.exceptions.ServerException;
import com.example.shitty_demo.core.mappers.MapperTo;
import com.example.shitty_demo.external.UncheckedCloseable;

import java.sql.ResultSet;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class DBHelper<Model extends Record> implements Request<Model, ResultSet> {
    private final DBConnector connector;

    public DBHelper(DBConnector connector) {
        this.connector = connector;
    }

    @Override
    public Stream<Model> get(String query, MapperTo<Model, ResultSet> mapper, List<?> attributes) throws ServerException {
        UncheckedCloseable close;
        try (
            final var connection = connector.getConnection();
            final var pSt = connection.prepareStatement(query)
        ) {
            close = UncheckedCloseable.wrap(connection);
            if (attributes != null) {
                for (var i = 0; i < attributes.size(); i ++) {
                    pSt.setObject(i + 1, attributes.get(i));
                }
            }
            close = close.nest(pSt);
            connection.setAutoCommit(false);
            pSt.setFetchSize(5000);
            final var resultSet = pSt.executeQuery();
            close = close.nest(resultSet);
            return StreamSupport.stream(new Spliterators.AbstractSpliterator<Model>(Long.MAX_VALUE, Spliterator.ORDERED) {
                @Override
                public boolean tryAdvance(Consumer<? super Model> action) {
                    try {
                        if (!resultSet.next()) {
                            return false;
                        }
                        action.accept(mapper.mapTo(resultSet));
                        return true;
                    } catch (Exception e) {
                        throw new RuntimeException();
                    }
                }
            }, false).onClose(close);
        } catch (Exception e) {
            throw new ServerException();
        }
    }

    @Override
    public void set(String query, List<?> attributes) throws ServerException {
        try (
                final var connection = connector.getConnection();
                final var stmt = connection.prepareStatement(query)
        ) {
            if (attributes != null) {
                for (var i = 0; i < attributes.size(); i ++) {
                    stmt.setObject(i + 1, attributes.get(i));
                }
            }
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new ServerException();
        }
    }
}
