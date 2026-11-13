package com.afridi.repository;

import com.afridi.model.Student;

import java.util.List;

public interface StudentRepository {

    void save(Student student);

    Student findById(int id);

    List<Student> findAll();
}