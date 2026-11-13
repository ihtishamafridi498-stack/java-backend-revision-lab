package com.afridi.grading;

public class GpaGrading implements GradingStrategy {

    @Override
    public String grade(double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100");
        }
        if (percentage >= 85) return "4.0";
        if (percentage >= 80) return "3.7";
        if (percentage >= 75) return "3.3";
        if (percentage >= 70) return "3.0";
        if (percentage >= 60) return "2.0";
        if (percentage >= 50) return "1.0";
        return "0.0";
    }
}