package com.afridi.config;

import com.afridi.grading.GpaGrading;
import com.afridi.grading.GradingStrategy;
import com.afridi.grading.PercentageGrading;
import com.afridi.model.ResultCard;
import com.afridi.model.Student;
import com.afridi.model.Subject;
import com.afridi.notification.ConsoleNotifier;
import com.afridi.notification.EmailNotifier;
import com.afridi.notification.ResultNotifier;
import com.afridi.repository.InMemoryStudentRepository;
import com.afridi.repository.StudentRepository;
import com.afridi.service.ResultService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.Map;

@Configuration
@PropertySource("classpath:result.properties")
public class AppConfig {

    @Value("${result.institution}")
    private String institution;

    @Value("${result.pass.percentage}")
    private double passPercentage;

    @Value("${result.email.from}")
    private String emailFrom;

    // ---------- Repository ----------

    @Bean
    public StudentRepository studentRepository() {
        InMemoryStudentRepository repo = new InMemoryStudentRepository();
        repo.setStudents(List.of(
                student(1, "Ali",   Map.of("Maths", 85, "Physics", 72, "English", 90)),
                student(2, "Sara",  Map.of("Maths", 30, "Physics", 25, "English", 40)),
                student(3, "Bilal", Map.of("Maths", 60, "Physics", 65, "English", 55))
        ));
        return repo;
    }

    // ---------- Grading ----------

    @Bean
    public GradingStrategy percentageGrading() {
        return new PercentageGrading();
    }

    @Bean
    public GradingStrategy gpaGrading() {
        return new GpaGrading();
    }

    // ---------- Notifiers ----------

    @Bean
    public ResultNotifier consoleNotifier() {
        return new ConsoleNotifier();
    }

    @Bean
    @Lazy
    public ResultNotifier emailNotifier() {
        EmailNotifier notifier = new EmailNotifier();
        notifier.setFromAddress(emailFrom);
        return notifier;
    }

    // ---------- Prototype ----------

    @Bean
    @Scope("prototype")
    public ResultCard resultCard() {
        return new ResultCard();
    }

    // ---------- Service ----------

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public ResultService resultService() {
        ResultService service = new ResultService(studentRepository());   // constructor injection
        service.setGradingStrategy(percentageGrading());                  // swap to gpaGrading() here
        service.setNotifier(consoleNotifier());                           // swap to emailNotifier() here
        service.setSubjects(List.of(
                new Subject("Maths", 100),
                new Subject("Physics", 100),
                new Subject("English", 100)
        ));
        service.setPassPercentage(passPercentage);
        service.setInstitutionName(institution);
        return service;
    }

    // ---------- Helper (not a bean) ----------

    private Student student(int id, String name, Map<String, Integer> marks) {
        Student s = new Student(id, name);
        s.setMarks(marks);
        return s;
    }
}