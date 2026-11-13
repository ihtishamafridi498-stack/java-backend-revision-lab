package com.afridi.repository;

import com.afridi.model.Student;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InMemoryStudentRepository implements StudentRepository {

    private final Map<Integer, Student> students = new LinkedHashMap<>();

    @Override
    public void save(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        students.put(student.getId(), student);
    }

    @Override
    public Student findById(int id) {
        Student student = students.get(id);
        if (student == null) {
            throw new IllegalArgumentException("No student with id " + id);
        }
        return student;
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(students.values());
    }

    public void setStudents(List<Student> list) {
        for (Student s : list) {
            save(s);
        }
    }
}