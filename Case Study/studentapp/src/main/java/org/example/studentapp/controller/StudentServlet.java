package org.example.studentapp.controller;

import org.example.studentapp.dao.StudentDAO;
import org.example.studentapp.model.Student;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/students")
public class StudentServlet extends HttpServlet {
    private StudentDAO studentDAO;

    @Override
    public void init() {
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "create":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
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
        }
    }

    // Hien thi danh sach sinh vien
    private void listStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Student> list = studentDAO.selectAllStudents();
        request.setAttribute("listStudent", list);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/stu/list.jsp");
        dispatcher.forward(request, response);
    }

    // Hien thi form tạo moi
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/stu/create.jsp");
        dispatcher.forward(request, response);
    }

    // Hien thi form edit
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

    // xoa sinh vien
    private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        studentDAO.deleteStudent(id);
        response.sendRedirect("students");
    }

    // Tim kiem
    private void searchStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Student> list = studentDAO.searchByNameOrCode(keyword);
        request.setAttribute("listStudent", list);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/stu/list.jsp");
        dispatcher.forward(request, response);
    }
}
