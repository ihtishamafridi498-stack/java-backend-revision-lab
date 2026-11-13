# Spring Core: Student Result System (XML Configuration)

A console application that calculates student results (total, percentage, grade, pass/fail) where **every object is created and wired by Spring from an XML file**. There is no `new` for services in the application code.

This project covers the first style of Spring bean management: **XML-based configuration**. The same app will later be rebuilt with Java-based configuration and annotations/Spring Boot.

## Tech Stack

- Java 21
- Spring Framework 6.1.14 (`spring-context`)
- Maven

## What it demonstrates

| Spring concept | Where it is used |
|---|---|
| IoC container | `ClassPathXmlApplicationContext` creates all beans |
| Constructor injection | `ResultService` receives `StudentRepository` |
| Setter injection | grading strategy, notifier, pass percentage, institution name |
| Programming to interfaces | `GradingStrategy`, `ResultNotifier`, `StudentRepository` |
| Swapping implementations | change one `ref` in XML, no Java changes |
| Properties file | `${...}` placeholders loaded from `result.properties` |
| Collection injection | `<list>` of students and subjects, `<map>` of marks |
| Inner beans | students and subjects defined inside `<list>` |
| Bean scopes | `singleton` (default) for services, `prototype` for `ResultCard` |
| Lifecycle callbacks | `init-method="start"`, `destroy-method="shutdown"` |
| Lazy initialization | `emailNotifier` is created only when requested |
| `ApplicationContextAware` | the singleton service gets a fresh prototype `ResultCard` each time |

## Concepts Revised

Short notes in simple words, written while revising for interviews.

### 1. IoC (Inversion of Control)
- Normally my code creates its own objects with `new`.
- With IoC, the **Spring container** creates and manages the objects, and gives them to my classes.
- The control of object creation moves from my code to the framework.

### 2. DI (Dependency Injection)
- If class A needs class B, then B is a **dependency** of A.
- DI means Spring creates B and **injects** it into A, so A does not create B itself.
- IoC is the idea. DI is the technique Spring uses to apply that idea.

```java
class ResultService {
    private final StudentRepository repository;

    ResultService(StudentRepository repository) { // Spring gives it
        this.repository = repository;
    }
}
```

**Benefits:** loose coupling, easy testing (we can pass a fake object), less boilerplate, reusable code.

### 3. Spring IoC Container
- The part of Spring that creates, configures and manages beans.
- It reads configuration (XML, Java config or annotations), creates the beans and injects their dependencies.

| Container | Key points |
|---|---|
| `BeanFactory` | Basic container. Lazy loading (creates a bean when asked). |
| `ApplicationContext` | Advanced container built on `BeanFactory`. Adds events, internationalization and annotation support. Creates singleton beans at startup. Used in most applications. |

A **bean** is any object that is created and managed by the Spring container.

### 4. Types of DI

| Type | When to use |
|---|---|
| Constructor injection | **Recommended.** Dependencies are required, fields can be `final`, easy to test. |
| Setter injection | For optional dependencies, or ones that can change later. |
| Field injection (`@Autowired` on a field) | Not recommended. Hides dependencies and is hard to test without Spring. |

### 5. Bean Scopes
Scope decides how many objects Spring creates for a bean and how long they live.

| Scope | Meaning |
|---|---|
| `singleton` (default) | One object per container, shared by everyone. Created at startup. |
| `prototype` | A new object every time it is requested. Created only when asked. |
| `request` | One per HTTP request (web only). |
| `session` | One per user session (web only). |
| `application` | One per ServletContext (web only). |
| `websocket` | One per WebSocket session (web only). |

```xml
<bean id="studentBean" class="com.afridi.model.Student" scope="singleton"/>
<bean id="resultCard" class="com.afridi.model.ResultCard" scope="prototype"/>
```

**Singleton vs prototype**

| Point | Singleton | Prototype |
|---|---|---|
| Objects created | One per container | New one every request |
| Created when | Container startup | When requested |
| Destroy callbacks | Spring calls them | Spring does **not** call them |
| Use for | Stateless beans (services, DAOs) | Stateful beans |

- A singleton bean is **not automatically thread-safe**, so keep it stateless.
- Spring singleton means one object per container per bean id. It is different from the Singleton design pattern (one object per JVM).
- **Problem:** if a singleton depends on a prototype, the prototype is injected only once. **Fix:** ask the context for a new one each time (`ApplicationContextAware`), or use `ObjectProvider`, `<lookup-method>` or a scoped proxy. This project uses `ApplicationContextAware`.

### 6. Lazy Initialization
- By default, singleton beans are **eager**: they are created when the container starts.
- A **lazy** bean is created only when it is first requested.

```xml
<!-- one bean -->
<bean id="emailNotifier" class="com.afridi.notification.EmailNotifier" lazy-init="true"/>

<!-- all beans in the file -->
<beans default-lazy-init="true" ...>
```

| Advantages | Disadvantages |
|---|---|
| Faster startup | Configuration errors show up late (at first use) |
| Saves memory for rarely used beans | First request that uses the bean is slower |

- Eager is the default because it finds errors early, at startup.
- `lazy-init` has no effect on prototype beans (they are already created on demand).
- If a lazy bean is injected into an eager singleton, it is created at startup anyway.

### 7. XML Configuration File Header
The top of a Spring XML file:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           https://www.springframework.org/schema/beans/spring-beans.xsd">
</beans>
```

| Part | Meaning |
|---|---|
| `<?xml ...?>` | XML declaration (version and encoding) |
| `<beans>` | Root tag. All bean definitions go inside it. |
| `xmlns="..."` | Default namespace: tells the parser `<bean>` belongs to Spring |
| `xmlns:xsi` | Needed to use the `xsi:schemaLocation` attribute |
| `xsi:schemaLocation` | Links each namespace to its XSD file (namespace, then XSD URL) |

- **XSD** is a file that defines which tags and attributes are allowed. It lets Spring and the IDE validate the XML.
- For other features, add one more namespace and one more schema pair, for example `xmlns:context` for `<context:component-scan>` and `<context:property-placeholder>`.
- If IntelliJ shows "URI is not registered", check that the `spring-context` dependency is in `pom.xml` and reload Maven.

### 8. Autowiring and Conflicts
- If there are two beans of the same type, Spring throws `NoUniqueBeanDefinitionException`.
- Fix with `primary="true"` (XML) or `@Primary`, or choose a specific bean with `@Qualifier("beanName")`.

## Project Structure

```
src/main/
├── java/com/afridi/
│   ├── Main.java
│   ├── model/
│   │   ├── Student.java
│   │   ├── Subject.java
│   │   └── ResultCard.java
│   ├── repository/
│   │   ├── StudentRepository.java            interface
│   │   └── InMemoryStudentRepository.java
│   ├── grading/
│   │   ├── GradingStrategy.java              interface
│   │   ├── PercentageGrading.java
│   │   └── GpaGrading.java
│   ├── notification/
│   │   ├── ResultNotifier.java               interface
│   │   ├── ConsoleNotifier.java
│   │   └── EmailNotifier.java
│   └── service/
│       └── ResultService.java
└── resources/
    ├── applicationContext.xml                bean definitions and wiring
    └── result.properties                     institution, pass mark, email
```

## How the beans are wired

```
resultService
 ├─ constructor-arg  -> studentRepository
 ├─ gradingStrategy  -> percentageGrading   (swap with gpaGrading)
 ├─ notifier         -> consoleNotifier     (swap with emailNotifier)
 ├─ subjects         -> <list> of Subject beans
 ├─ passPercentage   -> ${result.pass.percentage}
 └─ institutionName  -> ${result.institution}

resultCard (prototype) -> a new instance on every getBean call
```

## Grading Rules

| Percentage | PercentageGrading | GpaGrading |
|---|---|---|
| 85+ | A+ | 4.0 |
| 80-84 | A+ | 3.7 |
| 75-79 | A | 3.3 |
| 70-74 | A | 3.0 |
| 60-69 | B | 2.0 |
| 50-59 | C | 1.0 |
| below 50 | F | 0.0 |

A student passes if their percentage is at least `result.pass.percentage` (default 40).

## How to Run

**IntelliJ:** open the project, let Maven download the dependencies, and run `com.afridi.Main`.

**Requirements:** JDK 17 or higher (Spring 6), Maven.

## Sample Output

```
Result system started for ABC University
[CONSOLE] Ali: A+ (PASS)
----- RESULT CARD -----
Student    : Ali (id 1)
Total      : 247 / 300
Percentage : 82.33%
Grade      : A+
Result     : PASS
-----------------------
[CONSOLE] Sara: F (FAIL)
----- RESULT CARD -----
Student    : Sara (id 2)
Total      : 95 / 300
Percentage : 31.67%
Grade      : F
Result     : FAIL
-----------------------
[CONSOLE] Bilal: B (PASS)
----- RESULT CARD -----
Student    : Bilal (id 3)
Total      : 180 / 300
Percentage : 60.00%
Grade      : B
Result     : PASS
-----------------------
Same service?  true
Same card?     false
Result system closed
```

`Same service? true` shows the singleton scope. `Same card? false` shows the prototype scope.

## Experiments to try

1. **Swap grading:** in `applicationContext.xml`, change `ref="percentageGrading"` to `ref="gpaGrading"` and rerun. No Java changes needed.
2. **Swap notifier:** change `consoleNotifier` to `emailNotifier`.
3. **Change the pass mark:** edit `result.pass.percentage` in `result.properties`.
4. **Break a `ref`:** misspell a bean id and read the `NoSuchBeanDefinitionException`.
5. **Autowiring:** replace the grading `<property>` with `autowire="byType"` and observe `NoUniqueBeanDefinitionException`, then fix it with `primary="true"`.

## What I learned

- Spring creates objects and injects dependencies, so classes do not construct their own collaborators (Inversion of Control and Dependency Injection).
- Depending on interfaces makes behaviour configurable from XML.
- A singleton cannot directly hold a prototype bean, because the prototype would be injected only once. The service asks the context for a new one each time.
- Closing the context triggers `destroy-method`.

## Roadmap

- [x] XML-based configuration (this project)
- [ ] Java-based configuration (`@Configuration`, `@Bean`)
- [ ] Annotation-based configuration and Spring Boot