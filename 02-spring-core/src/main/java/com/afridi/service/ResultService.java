package com.afridi.service;

import com.afridi.grading.GradingStrategy;
import com.afridi.model.ResultCard;
import com.afridi.model.Student;
import com.afridi.model.Subject;
import com.afridi.notification.ResultNotifier;
import com.afridi.repository.StudentRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultService implements ApplicationContextAware {

    private final StudentRepository studentRepository;   // constructor injection
    private GradingStrategy gradingStrategy;              // setter injection
    private ResultNotifier notifier;                      // setter injection
    private List<Subject> subjects = new ArrayList<>();   // collection injection
    private double passPercentage;                        // from properties file
    private String institutionName;                       // from properties file
    private ApplicationContext context;                   // to get fresh prototype ResultCards

    public ResultService(StudentRepository studentRepository) {
        if (studentRepository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.studentRepository = studentRepository;
    }


    public void setGradingStrategy(GradingStrategy gradingStrategy) { this.gradingStrategy = gradingStrategy; }
    public void setNotifier(ResultNotifier notifier) { this.notifier = notifier; }
    public void setSubjects(List<Subject> subjects) { this.subjects = new ArrayList<>(subjects); }
    public void setPassPercentage(double passPercentage) { this.passPercentage = passPercentage; }
    public void setInstitutionName(String institutionName) { this.institutionName = institutionName; }

    @Override
    public void setApplicationContext(ApplicationContext context) { this.context = context; }


    public void start() {
        System.out.println("Result system started for " + institutionName);
    }

    public void shutdown() {
        System.out.println("Result system closed");
    }


    public ResultCard generateResult(int studentId) {
        Student student = studentRepository.findById(studentId);
        Map<String, Integer> marks = student.getMarks();

        int total = 0;
        int maxTotal = 0;
        for (Subject subject : subjects) {
            Integer obtained = marks.get(subject.getName());
            if (obtained == null) {
                throw new IllegalStateException(student.getName() + " has no marks for " + subject.getName());
            }
            if (obtained < 0 || obtained > subject.getMaxMarks()) {
                throw new IllegalStateException("Invalid marks for " + subject.getName() + ": " + obtained);
            }
            total += obtained;
            maxTotal += subject.getMaxMarks();
        }

        double percentage = (maxTotal == 0) ? 0 : (total * 100.0) / maxTotal;

        ResultCard card = context.getBean("resultCard", ResultCard.class);
        card.setStudent(student);
        card.setTotal(total);
        card.setMaxTotal(maxTotal);
        card.setPercentage(percentage);
        card.setGrade(gradingStrategy.grade(percentage));
        card.setPassed(percentage >= passPercentage);

        notifier.send(student.getName() + ": " + card.getGrade()
                + (card.isPassed() ? " (PASS)" : " (FAIL)"));
        return card;
    }

    public void printAll() {
        for (Student s : studentRepository.findAll()) {
            System.out.println(generateResult(s.getId()));
        }
    }
}