package com.afridi.grading;

public class PercentageGrading implements GradingStrategy {

    @Override
    public String grade(double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100");
        }
        if (percentage >= 80) return "A+";
        if (percentage >= 70) return "A";
        if (percentage >= 60) return "B";
        if (percentage >= 50) return "C";
        return "F";
    }
}