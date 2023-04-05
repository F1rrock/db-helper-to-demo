package com.example.shitty_demo.connections;

import com.example.shitty_demo.core.connectors.DBConnector;

import java.sql.Connection;
import java.sql.DriverManager;

public final class MySqlConnector implements DBConnector {
    private static final String URL = "";
    private static final String USER = "";
    private static final String PASS = "";
    @Override
    public Connection getConnection() throws Exception {
        return DriverManager.getConnection(
                URL,
                USER,
                PASS
        );
    }
}
