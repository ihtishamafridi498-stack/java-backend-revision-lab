package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Member {

    private final int memberId;
    private final String memberName;

    private List<Loan> loans=new ArrayList<>();

    public Member(int memberId,String memberName){
        if (memberId <= 0) {
            throw new IllegalArgumentException("Id must be greater than zero");
        }
        if (memberName == null || memberName.isBlank()) {
            throw new IllegalArgumentException("name cannot be empty");
        }
        this.memberId=memberId;
        this.memberName=memberName;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public abstract int getMaxItems();

    public abstract double getFinePerDay();

    public boolean canBorrow() {
        return loans.size() < getMaxItems();
    }

    public void addLoan(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("Loan cannot be null");
        }
        loans.add(loan);
    }

    public void removeLoan(Loan loan) {
        loans.remove(loan);
    }

    public List<Loan> getLoans() {
        return new ArrayList<>(loans);
    }
}
