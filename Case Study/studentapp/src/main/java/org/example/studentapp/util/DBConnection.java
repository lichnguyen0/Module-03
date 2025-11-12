package org.example.studentapp.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
/*Nhiệm vụ chính DBConnection:
    • Tạo kết nối (Connection) tới database (MySQL, PostgreSQL, SQL Server…).
    • Trả về Connection để các DAO (Data Access Object) sử dụng thực hiện các câu lệnh SQL (SELECT, INSERT, UPDATE, DELETE).
    • Đóng kết nối khi không còn sử dụng, để tránh rò rỉ tài nguyên.*/

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
