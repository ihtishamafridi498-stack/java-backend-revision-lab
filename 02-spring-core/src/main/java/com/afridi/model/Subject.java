package com.afridi.model;

public class Subject {

    private String name;
    private int maxMarks;

    public Subject() { }

    public Subject(String name, int maxMarks) {
        this.name = name;
        this.maxMarks = maxMarks;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getMaxMarks() { return maxMarks; }
    public void setMaxMarks(int maxMarks) { this.maxMarks = maxMarks; }

    @Override
    public String toString() {
        return name + " (max " + maxMarks + ")";
    }
}