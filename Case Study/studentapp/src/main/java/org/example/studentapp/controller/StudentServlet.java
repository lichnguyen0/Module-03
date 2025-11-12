package org.example.studentapp.controller;

import org.example.studentapp.dao.StudentDAO;
import org.example.studentapp.model.Student;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/students") // "/students" tên tự đăt không phải tên gọi trưc tiếp form or database
public class StudentServlet extends HttpServlet {
    private StudentDAO studentDAO;

    @Override
    public void init() {
        studentDAO = new StudentDAO();
    }

    @Override // doget này có nhiệm vụ lấy dữ liệu hiển thị form: VD: "create" hiển thị form thêm sinh viên mới
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "create":  // "create" lấy từ dòng này <form action="students?action=create" method="post"> của file create.jsp
                showNewForm(request, response);
                break;
            case "edit": // tương tự
                showEditForm(request, response);
                break;
            case "delete": // tương tự
                deleteStudent(request, response);
                break;
            case "search":
                searchStudent(request, response);
                break;
            default:
                listStudent(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "create":
                insertStudent(request, response);
                break;
            case "edit":
                updateStudent(request, response);
                break;
            /*tại sao Không có "delete" và "search" vì:
            "delete": theo chuẩn REST, nên dùng POST/DELETE riêng, không dùng GET vì GET chỉ “lấy dữ liệu”.
            "search": chỉ là lấy dữ liệu để hiển thị, không thay đổi DB → hợp với GET, không cần POST.*/
        }
    }

    // phương thưc Hien thi danh sach sinh vien của doGet
    private void listStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Student> list = studentDAO.selectAllStudents(); //gọi StudentDAO.<phươngthức> để lấy tất cả sinh viên từ bảng students trong database.
        request.setAttribute("listStudent", list); //setAttribute gắn dữ liệu vào request với key "listStudent".
                                                         //Mục đích: để truyền dữ liệu này sang JSP để hiển thị.
                                                        //Trong JSP bạn sẽ dùng <c:forEach var="student" items="${listStudent}"> hoặc JSTL <c:forEach> để lặp và hiển thị từng sinh viên.
        RequestDispatcher dispatcher = request.getRequestDispatcher("/stu/list.jsp"); //tạo đối tượng Dispatcher để chuyển request tới file JSP list.jsp.
        dispatcher.forward(request, response); // forward request và dữ liệu sang JSP.
                                                //JSP sẽ nhận listStudent từ request và hiển thị lên trình duyệt.
    }

    // Hien thi form tạo moi của doget
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/stu/create.jsp");
        dispatcher.forward(request, response);
    }

    // Hien thi form edit của doget
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Student existingStudent = studentDAO.selectStudent(id);
        request.setAttribute("student", existingStudent);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/stu/edit.jsp");
        dispatcher.forward(request, response);
    }

    // Them thanh vien moi (POST)
    private void insertStudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String code = request.getParameter("studentCode");
        String name = request.getParameter("fullName");
        String className = request.getParameter("className");
        double gpa = Double.parseDouble(request.getParameter("gpa"));
        Student student = new Student(code, name, className, gpa);
        studentDAO.insertStudent(student);
        response.sendRedirect("students");
    }

    // Cap nhat sinh vien (POST)
    private void updateStudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String code = request.getParameter("studentCode");
        String name = request.getParameter("fullName");
        String className = request.getParameter("className");
        double gpa = Double.parseDouble(request.getParameter("gpa"));
        Student student = new Student(id, code, name, className, gpa);
        studentDAO.updateStudent(student);
        response.sendRedirect("students");
    }

    // xoa sinh vien của doGet
    private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        studentDAO.deleteStudent(id);
        response.sendRedirect("students");
    }

    // Tim kiem của doGet
    private void searchStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Student> list = studentDAO.searchByNameOrCode(keyword);
        request.setAttribute("listStudent", list);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/stu/list.jsp");
        dispatcher.forward(request, response);
    }
}
