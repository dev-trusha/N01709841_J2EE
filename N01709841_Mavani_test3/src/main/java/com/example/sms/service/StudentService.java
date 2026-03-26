package com.example.sms.service;

import com.example.sms.model.Student;

import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();
    void addStudent(Student student);
    Student getStudentById(int id);
    void updateStudent(Student student);
    void deleteStudent(int id);
}
