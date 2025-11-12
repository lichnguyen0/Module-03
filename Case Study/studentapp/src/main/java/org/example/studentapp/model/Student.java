package org.example.studentapp.model;

public class Student {
    private int id;
    private String studentCode;
    private String fullName;
    private String className;
    private double gpa;

    public Student() {
    }

    public Student(String studentCode, String fullName, String className, double gpa) {
        this.studentCode = studentCode;
        this.fullName = fullName;
        this.className = className;
        this.gpa = gpa;
    }

    public Student(int id, String studentCode, String fullName, String className, double gpa) {
        this.id = id;
        this.studentCode = studentCode;
        this.fullName = fullName;
        this.className = className;
        this.gpa = gpa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
}