package org.example.studentapp.dao;

import org.example.studentapp.util.DBConnection;

import java.sql.*;

public class AdminDAO {

   /* private String jdbcURL = "jdbc:mysql://localhost:3306/studentdb1?useSSL=false&serverTimezone=UTC";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";

    protected Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/

    public boolean checkLogin(String username, String password) {
        String sql = "SELECT * FROM admins WHERE username=? AND password=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // login thành công nếu có kết quả
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

/*
* Nhiệm vụ chính của AdminDAO:
• Kết nối tới database:
    •Thông qua DBConnection.getConnection(), lớp này có thể lấy Connection để thao tác với database.
    •Đảm bảo không phải viết lại mã kết nối mỗi lần.
• Thực hiện kiểm tra đăng nhập:
    •Phương thức checkLogin(String username, String password) truy vấn bảng admins để xem có tồn tại admin với username và password đã nhập hay không.
    •Trả về true nếu tìm thấy (login thành công), false nếu không tìm thấy (login thất bại).*/
