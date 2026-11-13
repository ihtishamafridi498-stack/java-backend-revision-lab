# Core Java Revision

A structured revision of core Java fundamentals, with hands-on projects for each stage.

## Revision Roadmap

| # | Topic | Status | Where it is practiced |
|---|-------|--------|-----------------------|
| 1 | Classes and Objects | Done | Student Management, Library |
| 2 | Constructors and Constructor Overloading | Done | Student Management, Library |
| 3 | Object References and Memory (Stack & Heap) | Done | Library |
| 4 | Encapsulation | Done | Library |
| 5 | Inheritance | Done | Library |
| 6 | Polymorphism | Done | Library |
| 7 | Abstraction | Done | Library |
| 8 | Interfaces and Abstract Classes | Done | Library |

## Topics Covered

### 1. Classes and Objects
- Understanding classes and objects
- Instance variables and methods
- Creating objects using `new`
- Private instance variables

### 2. Constructors
- Parameterized and no-argument constructors
- Constructor overloading
- Constructor chaining using `this()`

### 3. Object References and Memory
- References live on the stack, objects live on the heap
- Java is always pass-by-value (the reference is copied)
- `==` vs `.equals()`, `null` references, garbage collection

### 4. Encapsulation
- Private fields with controlled access
- Validation inside constructors and methods
- Defensive copies (getters return copies of lists)

### 5. Inheritance
- `extends`, `super(...)` and constructor chaining across classes
- Method overriding with `@Override`

### 6. Polymorphism
- Compile-time (overloading) and runtime (overriding) polymorphism
- Parent reference holding a child object

### 7. Abstraction
- Abstract classes and abstract methods
- Shared rules in the parent, special rules in the child

### 8. Interfaces and Abstract Classes
- Interfaces as contracts, `default` methods
- A class implementing multiple interfaces
- Choosing between an abstract class and an interface

## Projects

### Student Management System
`implementation/student-management`

- Student class with private fields
- Multiple constructors with chaining to avoid duplicate initialization
- `displayDetails()` and `isEligibleForAdmission()` methods

### Library Management System
`implementation/library-management`

A console application that combines topics 1 to 8.

**Features**
- Add books, magazines and DVDs
- Add student and teacher members
- Borrow and return items with automatic due dates
- Late fine calculation
- Search by title, author or ISBN
- List available items and overdue loans

**Business rules**
- An item that is already borrowed cannot be borrowed again
- Members cannot exceed their borrowing limit
- Duplicate item and member ids are rejected
- Due date = issue date + the item's loan days
- Fine = overdue days x the member's fine per day

| | Max items | Fine per day | Loan period |
|---|---|---|---|
| Student | 3 | Rs 10 | |
| Teacher | 5 | Rs 5 | |
| Book | | | 14 days |
| Magazine | | | 7 days |
| DVD | | | 3 days |

**Project structure**
```
library-management/src/
├── Main2.java              console menu
├── contract/
│   ├── Borrowable.java     interface
│   ├── Searchable.java     interface
│   └── Fineable.java       interface (with a default method)
├── model/
│   ├── LibraryItem.java    abstract class
│   ├── Book.java
│   ├── Magazine.java
│   ├── DVD.java
│   ├── Member.java         abstract class
│   ├── StudentMember.java
│   ├── TeacherMember.java
│   └── Loan.java
└── service/
    └── Library.java        business logic
```

**Where each concept is used**

| Concept | In the project |
|---|---|
| Classes and Objects | Every class in `model` |
| Constructors | Validation in constructors, `this(...)` and `super(...)` chaining |
| References and Memory | `Loan` holds references to the same `Member` and `LibraryItem` objects |
| Encapsulation | Private fields, no raw setters, getters return copies |
| Inheritance | `Book`, `Magazine`, `DVD` extend `LibraryItem`; `StudentMember`, `TeacherMember` extend `Member` |
| Polymorphism | `getLoanDays()` and `matches()` run the correct version for each item type |
| Abstraction | Abstract `LibraryItem` and `Member` |
| Interfaces | `Borrowable`, `Searchable`, `Fineable` |

**How to run**

From IntelliJ: open the project and run `Main2`.

From the terminal:
```bash
cd implementation/library-management/src
javac -d ../out contract/*.java model/*.java service/*.java Main2.java
java -cp ../out Main2
```

**Sample output**
```
===== LIBRARY MENU =====
1. Add item
2. Add member
3. Borrow item
4. Return item
5. Search
6. List available items
7. List overdue loans
0. Exit
Choose: 3
Member id: 1
Item id: 1
Borrowed! Due on 2026-11-27

Choose: 3
Member id: 1
Item id: 1
Error: Item 'Clean Code' is not available

Choose: 5
Keyword: martin
Book{id=1, title='Clean Code', available=false} [author=Robert Martin, isbn=978-0132350884]

Choose: 4
Member id: 1
Item id: 1
Returned on time. No fine.
```
