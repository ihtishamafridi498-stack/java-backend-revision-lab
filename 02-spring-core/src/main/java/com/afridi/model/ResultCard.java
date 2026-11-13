package com.afridi.model;

public class ResultCard {

    private Student student;
    private int total;
    private int maxTotal;
    private double percentage;
    private String grade;
    private boolean passed;

    public ResultCard() { }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }

    public int getMaxTotal() { return maxTotal; }
    public void setMaxTotal(int maxTotal) { this.maxTotal = maxTotal; }

    public double getPercentage() { return percentage; }
    public void setPercentage(double percentage) { this.percentage = percentage; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public boolean isPassed() { return passed; }
    public void setPassed(boolean passed) { this.passed = passed; }

    @Override
    public String toString() {
        return "----- RESULT CARD -----\n"
                + "Student    : " + student.getName() + " (id " + student.getId() + ")\n"
                + "Total      : " + total + " / " + maxTotal + "\n"
                + "Percentage : " + String.format("%.2f", percentage) + "%\n"
                + "Grade      : " + grade + "\n"
                + "Result     : " + (passed ? "PASS" : "FAIL") + "\n"
                + "-----------------------";
    }
}