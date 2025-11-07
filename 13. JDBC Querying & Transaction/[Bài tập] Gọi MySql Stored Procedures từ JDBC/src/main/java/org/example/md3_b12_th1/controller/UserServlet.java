package org.example.md3_b12_th1.controller;

import org.example.md3_b12_th1.dao.UserDAO;
import org.example.md3_b12_th1.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = "/users")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    insertUser(request, response);
                    break;
                case "edit":
                    updateUser(request, response);
                    break;
                case "edit-sp":
                    updateUserBySP(request, response);
                    break;

            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "create":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteUser(request, response);
                    break;
                case "search":
                    searchUser(request, response);
                    break;
                case "sort":
                    sortUser(request, response);
                    break;
                case "test-without-tran":
                    testWithoutTran(request, response);
                    break;
                case "test-use-tran":
                    testUseTran(request, response);
                    break;
                case "list-sp":
                    listUserBySP(request, response);
                    break;
                case "delete-sp":
                    deleteUserBySP(request, response);
                    break;

                default:
                    listUser(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<User> listUser = userDAO.selectAllUsers();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/create.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = userDAO.getUserById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/edit.jsp");
        request.setAttribute("user", existingUser);
        dispatcher.forward(request, response);

    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        String add = request.getParameter("add");
        String edit = request.getParameter("edit");
        String delete = request.getParameter("delete");
        String view = request.getParameter("view");
        List<Integer> permissions = new ArrayList<>();
        if (add != null){
            permissions.add(1);
        }
        if (edit != null){
            permissions.add(2);
        }
        if (delete != null){
            permissions.add(3);
        }
        if (view != null){
            permissions.add(4);
        }

        User newUser = new User(name, email, country);
        //userDAO.insertUser(newUser);
        userDAO.addUserTransaction(newUser, permissions);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/create.jsp");
        dispatcher.forward(request, response);
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        User book = new User(id, name, email, country);
        userDAO.updateUser(book);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/edit.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        userDAO.deleteUser(id);

        List<User> listUser = userDAO.selectAllUsers();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/list.jsp");
        dispatcher.forward(request, response);
    }

    private void searchUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String country = request.getParameter("country");
        List<User> users = userDAO.searchByCountry(country);
        request.setAttribute("listUser", users);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/list.jsp");
        dispatcher.forward(request, response);
    }

    private void sortUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> users = userDAO.sortByName();
        request.setAttribute("listUser", users);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/list.jsp");
        dispatcher.forward(request, response);
    }

    private void testWithoutTran(HttpServletRequest request, HttpServletResponse response) {
        userDAO.insertUpdateWithoutTransaction();
    }

    private void testUseTran(HttpServletRequest request, HttpServletResponse response) {
        userDAO.insertUpdateUseTransaction(); // gọi phương thức có transaction
    }

    // ------------------------- STORED PROCEDURE -------------------------

    // 1️⃣ Hiển thị danh sách users (gọi procedure get_all_users)
    private void listUserBySP(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<User> listUser = userDAO.getAllUsersBySP();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/list.jsp");
        dispatcher.forward(request, response);
    }

    // 2️⃣ Cập nhật user (gọi procedure update_user_by_id)
    private void updateUserBySP(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        User user = new User(id, name, email, country);
        userDAO.updateUserBySP(user);
        response.sendRedirect("users?action=list-sp");
    }

    // 3️⃣ Xoá user (gọi procedure delete_user_by_id)
    private void deleteUserBySP(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        userDAO.deleteUserBySP(id);
        response.sendRedirect("users?action=list-sp");
    }


}
