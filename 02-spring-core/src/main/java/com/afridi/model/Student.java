package com.afridi.model;

import java.util.HashMap;
import java.util.Map;

public class Student {

    private int id;
    private String name;
    private Map<String, Integer> marks = new HashMap<>();

    public Student() { }

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Map<String, Integer> getMarks() {
        return new HashMap<>(marks);          // defensive copy (Topic 4)
    }

    public void setMarks(Map<String, Integer> marks) {
        this.marks = new HashMap<>(marks);    // copy what Spring gives us
    }

    public void addMark(String subject, int value) {
        marks.put(subject, value);
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', marks=" + marks + "}";
    }
}