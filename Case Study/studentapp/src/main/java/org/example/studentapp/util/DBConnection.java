package org.example.studentapp.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String jdbcURL = "jdbc:mysql://localhost:3306/studentdb1?useSSL=false&serverTimezone=UTC";
    private static final String jdbcUsername = "root";
    private static final String jdbcPassword = "123456";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
