package com.company.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public abstract class Dao {
    private static final ResourceBundle settings = ResourceBundle.getBundle("settings");
    private static final ThreadLocal<Connection> connection = ThreadLocal
            .withInitial(() -> {
                try {
                    return DriverManager.getConnection(
                            settings.getString("datasource.url"),
                            settings.getString("datasource.username"),
                            settings.getString("datasource.password")
                    );
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

    protected Connection getConnection() {
        return connection.get();
    }
}
