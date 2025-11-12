package org.example.studentapp.controller;

import org.example.studentapp.dao.AdminDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login") // "/login" tên tự đăt không phải tên gọi trưc tiếp form or database
public class LoginServlet extends HttpServlet {
    private AdminDAO adminDAO;

    public void init() {
        adminDAO = new AdminDAO();
    }

    @Override // doget là lấy dữ liệu và hiển thị form login lên browser. nhưng không kiểm tra usernamer/password
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/stu/login.jsp").forward(request, response);
    }

     /* dopost: xử lý login, kiểm tra username/password, tạo session truy vấn database để xem có tồn tại admin với username/password đó không.
               Nếu có → login thành công, tạo session.
               Nếu không → báo lỗi.*/
     @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username"); // lấy từ giá trị người dùng nhập trong form <input name="username">
        String password = request.getParameter("password"); // và <input name="password">).

         //adminDAO.checkLogin() truy vấn database để xem có tồn tại admin với username và password tương ứng hay không.
        if (adminDAO.checkLogin(username, password)) { //Nếu login thành công:
            HttpSession session = request.getSession(); // Tạo session cho user → lưu trữ thông tin đăng nhập trên server.
            session.setAttribute("username", username);//lưu username để dùng cho các trang khác, ví dụ xác thực quyền truy cập.
            response.sendRedirect("students"); //chuyển hướng người dùng sang trang danh sách sinh viên.
                                                        //Lưu ý: sendRedirect tạo một request mới tới URL /students.
                                                        //nghĩa là chuyển hướng đến file StudentServlet, ccụ thể là @WebServlet("/students")
        } else { //Nếu login thất bại:
            request.setAttribute("errorMessage", "Invalid username or password!"); //Gán thông báo lỗi vào request.
            request.getRequestDispatcher("/stu/login.jsp").forward(request, response); //gửi lại request tới login.jsp để hiển thị form cùng thông báo lỗi.
                                                                                             //Người dùng không rời khỏi URL /login, chỉ thấy thông báo lỗi trên trang login.
        }
    }
}