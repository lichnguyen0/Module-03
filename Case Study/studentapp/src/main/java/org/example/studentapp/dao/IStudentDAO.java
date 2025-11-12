package org.example.studentapp.dao;

import org.example.studentapp.model.Student;

import java.util.List;

public interface IStudentDAO {
    void insertStudent(Student student);

    Student selectStudent(int id);

    List<Student> selectAllStudents();

    boolean updateStudent(Student student);

    boolean deleteStudent(int id);

    List<Student> searchByNameOrCode(String keyword);
}
