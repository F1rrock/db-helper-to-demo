package com.example.shitty_demo.core.connectors;

import java.sql.Connection;

@FunctionalInterface
public interface DBConnector {
    Connection getConnection() throws Exception;
}
