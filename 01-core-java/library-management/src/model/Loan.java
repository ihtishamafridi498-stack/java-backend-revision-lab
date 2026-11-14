package model;

import contract.Fineable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan implements Fineable {

    private final LibraryItem item;
    private final Member member;
    private final LocalDate issueDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;

    public Loan(LibraryItem item, Member member, LocalDate issueDate) {
        if (item == null || member == null || issueDate == null) {
            throw new IllegalArgumentException("Item, member and issue date cannot be null");
        }
        this.item = item;
        this.member = member;
        this.issueDate = issueDate;
        this.dueDate = issueDate.plusDays(item.getLoanDays());
    }


    public Loan(LibraryItem item, Member member) {
        this(item, member, LocalDate.now());
    }

    // Getters
    public LibraryItem getItem() {
        return item;
    }

    public Member getMember() {
        return member;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isReturned() {
        return returnDate != null;
    }

    public void closeLoan(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Return date cannot be null");
        }
        if (returnDate != null) {
            throw new IllegalStateException("Loan is already closed");
        }
        if (date.isBefore(issueDate)) {
            throw new IllegalArgumentException("Return date cannot be before issue date");
        }
        this.returnDate = date;
    }

    public int daysOverdue() {
        LocalDate end = (returnDate != null) ? returnDate : LocalDate.now();
        if (end.isAfter(dueDate)) {
            return (int) ChronoUnit.DAYS.between(dueDate, end);
        }
        return 0;
    }

    public boolean isOverdue() {
        return daysOverdue() > 0;
    }

    @Override
    public double calculateFine() {
        return daysOverdue() * member.getFinePerDay();
    }

    @Override
    public String toString() {
        return "Loan{" + item.getTitle() +
                ", member=" + member.getMemberName()+
                ", issued=" + issueDate +
                ", due=" + dueDate +
                ", returned=" + (returnDate == null ? "not yet" : returnDate) +
                '}';
    }
}