package org.example.md3_b12_th1.dao;

import org.example.md3_b12_th1.model.User;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//Khai báo lớp UserDao (Data Access Object) chịu trách nhiệm thao tác với bảng users trong DB.
public class UserDAO implements IUserDAO {
    //    Lưu thông tin kết nối MySQL: URL, username, password.
    private String jdbcURL = "jdbc:mysql://localhost:3306/ddemo?useSSL=false&serverTimezone=UTC";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";

    //    Khai báo các câu lệnh SQL dùng nhiều lần, dùng ? cho các tham số để PreparedStatement có thể gán giá trị an toàn.
    private static final String INSERT_USERS_SQL = "INSERT INTO users (name, email, country) VALUES (?, ?, ?);";
    private static final String SELECT_USER_BY_ID = "select id,name,email,country from users where id =?";
    private static final String SELECT_ALL_USERS = "select * from users";
    private static final String DELETE_USERS_SQL = "delete from users where id = ?;";
    private static final String UPDATE_USERS_SQL = "update users set name = ?,email= ?, country =? where id = ?;";

    //    [Bài tập] Gọi MySql Stored Procedures từ JDBC
    private static final String GET_ALL_USERS_SP = "{CALL get_all_users()}";
    private static final String UPDATE_USER_SP = "{CALL update_user_by_id(?, ?, ?, ?)}";
    private static final String DELETE_USER_SP = "{CALL delete_user_by_id(?)}";

    private static final String SQL_INSERT = "INSERT INTO Employee (NAME, SALARY, CREATED_DATE) VALUES (?,?,?)";
    private static final String SQL_UPDATE = "UPDATE Employee SET SALARY=? WHERE NAME=?"; // Lệnh update lỗi để test
    private static final String SQL_TABLE_CREATE = "CREATE TABLE Employee"
            + "("
            + "ID int NOT NULL AUTO_INCREMENT,"
            + "NAME varchar(120) NOT NULL,"
            + "SALARY int(220) NOT NULL,"
            + "CREATED_DATE datetime,"
            + "PRIMARY KEY (ID)"
            + ")";
    private static final String SQL_TABLE_DROP = "DROP TABLE IF EXISTS Employee";
    // ================================================================================

    public UserDAO() {
    }

    //    Phương thức tạo kết nối DB:
//    Tạo kết nối DB để các phương thức khác sử dụng.
    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //    Thêm User:
//Sử dụng try-with-resources: tự động đóng Connection và PreparedStatement.
//    executeUpdate() dùng cho INSERT, UPDATE, DELETE.
    public void insertUser(User user) throws SQLException {
        System.out.println(INSERT_USERS_SQL);
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getCountry());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public User selectUser(int id) {
        User user = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                user = new User(id, name, email, country);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }

    public List<User> selectAllUsers() {

        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                users.add(new User(id, name, email, country));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }

    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getCountry());
            statement.setInt(4, user.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    public List<User> searchByCountry(String country) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE country LIKE ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + country + "%");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String c = rs.getString("country");
                users.add(new User(id, name, email, c));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<User> sortByName() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users ORDER BY name ASC";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                users.add(new User(id, name, email, country));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User getUserById(int id) {
        User user = null;
        String query = "{CALL get_user_by_id(?)}";

        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall(query)) {
            callableStatement.setInt(1, id);
            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                user = new User(id, name, email, country);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }

    @Override
    public void insertUserStore(User user) throws SQLException {
        String query = "{CALL insert_user(?,?,?)}";

        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall(query);) {
            callableStatement.setString(1, user.getName());
            callableStatement.setString(2, user.getEmail());
            callableStatement.setString(3, user.getCountry());
            System.out.println(callableStatement);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public void addUserTransaction(User user, List<Integer> permissions) {
        Connection conn = null;
        PreparedStatement psInsertUser = null;
        PreparedStatement psAssignPermission = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false); // 🔹 Bắt đầu Transaction

            //  Thêm user mới
            psInsertUser = conn.prepareStatement(INSERT_USERS_SQL, Statement.RETURN_GENERATED_KEYS);
            psInsertUser.setString(1, user.getName());
            psInsertUser.setString(2, user.getEmail());
            psInsertUser.setString(3, user.getCountry());
            int rowAffected = psInsertUser.executeUpdate();

            // Lấy id user mới được thêm
            rs = psInsertUser.getGeneratedKeys();
            int userId = 0;
            if (rs.next()) {
                userId = rs.getInt(1);
            }

            //  Giả sử thêm quyền cho user
            if (rowAffected == 1) {
                //String sqlPivot = "INSERT INTO user_permision(user_id, permision_id) VALUES (?, ?)"; //đúng
                String sqlPivot = "INSERT INTO user_permission_fake(user_id, permision_id) VALUES (?, ?)"; // sai

                psAssignPermission = conn.prepareStatement(sqlPivot);

                for (int permisionId : permissions) {
                    psAssignPermission.setInt(1, userId);

                    // ⚠ Cố tình gây lỗi SQL ở đây để test rollback
                    // Ví dụ: cột "permision_id" viết sai thành "permision_idx"
                    // Khi chạy sẽ lỗi, rollback toàn bộ
                    psAssignPermission.setInt(2, permisionId);
                    psAssignPermission.executeUpdate();
                }

                //  Nếu không lỗi thì commit
                conn.commit();
                System.out.println(" Transaction thành công!");
            } else {
                conn.rollback();
                System.out.println(" Thêm user thất bại, rollback!");
            }

        } catch (SQLException ex) {
            try {
                if (conn != null) {
                    conn.rollback(); //  Có lỗi → rollback
                    System.out.println(" Đã rollback do lỗi SQL!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
        } finally {
            // Đóng tài nguyên
            try {
                if (rs != null) rs.close();
                if (psInsertUser != null) psInsertUser.close();
                if (psAssignPermission != null) psAssignPermission.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void insertUpdateWithoutTransaction() {
        try (Connection conn = getConnection();
             Statement statement = conn.createStatement();
             PreparedStatement psInsert = conn.prepareStatement(SQL_INSERT);
             PreparedStatement psUpdate = conn.prepareStatement(SQL_UPDATE)) {

            // Xóa bảng cũ và tạo bảng mới
            statement.execute(SQL_TABLE_DROP);
            statement.execute(SQL_TABLE_CREATE);

            // Chèn bản ghi đầu tiên
            psInsert.setString(1, "Quynh");
            psInsert.setInt(2, 10);
            psInsert.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            psInsert.execute();

            // Chèn bản ghi thứ hai
            psInsert.setString(1, "Ngan");
            psInsert.setInt(2, 20);
            psInsert.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            psInsert.execute();

            // Lệnh UPDATE lỗi (tham số sai)
            psUpdate.setInt(1, 999); // ⚠ lỗi cố tình để test không dùng transaction
            psUpdate.setString(2, "Quynh");
            psUpdate.execute();

        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console
        }
    }

    @Override
    public void insertUpdateUseTransaction() {
        try (Connection conn = getConnection();
             Statement statement = conn.createStatement();
             PreparedStatement psInsert = conn.prepareStatement(SQL_INSERT);
             PreparedStatement psUpdate = conn.prepareStatement(SQL_UPDATE)) {

            // Xóa bảng cũ và tạo bảng mới
            statement.execute(SQL_TABLE_DROP);
            statement.execute(SQL_TABLE_CREATE);

            // ===============================
            // ⚠ Bắt đầu transaction
            conn.setAutoCommit(false); // default là true

            // Chèn bản ghi đầu tiên
            psInsert.setString(1, "Quynh");
            psInsert.setBigDecimal(2, new BigDecimal(10));
            psInsert.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            psInsert.execute();

            // Chèn bản ghi thứ hai
            psInsert.setString(1, "Ngan");
            psInsert.setBigDecimal(2, new BigDecimal(20));
            psInsert.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            psInsert.execute();

            //  Lệnh UPDATE (sửa tham số đúng để không lỗi)
            psUpdate.setBigDecimal(1, new BigDecimal(999.99)); // sửa tham số 1, không phải 2
            psUpdate.setString(2, "Quynh");
            psUpdate.execute();

            // Commit transaction
            conn.commit();

            // Reset auto commit
            conn.setAutoCommit(true);
            // ===============================

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    [Bài tập] Gọi MySql Stored Procedures từ JDBC
    @Override
    public List<User> getAllUsersBySP() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall(GET_ALL_USERS_SP)) {

            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                users.add(new User(id, name, email, country));
            }
        }
        return users;
    }

    @Override
    public boolean updateUserBySP(User user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall(UPDATE_USER_SP)) {

            callableStatement.setInt(1, user.getId());
            callableStatement.setString(2, user.getName());
            callableStatement.setString(3, user.getEmail());
            callableStatement.setString(4, user.getCountry());

            rowUpdated = callableStatement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    @Override
    public boolean deleteUserBySP(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall(DELETE_USER_SP)) {

            callableStatement.setInt(1, id);
            rowDeleted = callableStatement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
        User user = new User("Test Transaction", "test@gmail.com", "Vietnam");
        List<Integer> permissions = new ArrayList<>();
        permissions.add(1);
        permissions.add(2);

        dao.addUserTransaction(user, permissions);
    }

}