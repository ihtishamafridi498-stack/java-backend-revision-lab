package service;

import contract.Searchable;
import model.LibraryItem;
import model.Loan;
import model.Member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Library {

    private final List<LibraryItem> items = new ArrayList<>();
    private final List<Member> members = new ArrayList<>();
    private final List<Loan> loans = new ArrayList<>();


    public void addItem(LibraryItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (findItem(item.getId()) != null) {
            throw new IllegalArgumentException("Item id " + item.getId() + " already exists");
        }
        items.add(item);
    }

    public void addMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        if (findMember(member.getMemberId()) != null) {
            throw new IllegalArgumentException("Member id " + member.getMemberId() + " already exists");
        }
        members.add(member);
    }


    public Loan borrowItem(int memberId, int itemId) {
        Member member = getMemberOrThrow(memberId);
        LibraryItem item = getItemOrThrow(itemId);

        if (!item.isAvailable()) {
            throw new IllegalStateException("Item '" + item.getTitle() + "' is not available");
        }
        if (!member.canBorrow()) {
            throw new IllegalStateException(member.getMemberName() + " has reached the limit of "
                    + member.getMaxItems() + " items");
        }

        Loan loan = new Loan(item, member);
        item.markBorrowed();
        member.addLoan(loan);
        loans.add(loan);
        return loan;
    }

    public double returnItem(int memberId, int itemId) {
        Member member = getMemberOrThrow(memberId);
        LibraryItem item = getItemOrThrow(itemId);

        Loan loan = findActiveLoan(member, item);
        if (loan == null) {
            throw new IllegalStateException(member.getMemberName() + " has not borrowed '"
                    + item.getTitle() + "'");
        }

        loan.closeLoan(LocalDate.now());
        item.markReturned();
        member.removeLoan(loan);
        return loan.calculateFine();
    }


    public List<Searchable> search(String keyword) {
        List<Searchable> results = new ArrayList<>();
        for (LibraryItem item : items) {
            if (item.matches(keyword)) {
                results.add(item);
            }
        }
        return results;
    }

    public List<LibraryItem> listAvailableItems() {
        List<LibraryItem> available = new ArrayList<>();
        for (LibraryItem item : items) {
            if (item.isAvailable()) {
                available.add(item);
            }
        }
        return available;
    }

    public List<Loan> listOverdueLoans() {
        List<Loan> overdue = new ArrayList<>();
        for (Loan loan : loans) {
            if (!loan.isReturned() && loan.isOverdue()) {
                overdue.add(loan);
            }
        }
        return overdue;
    }


    public List<LibraryItem> getItems() {
        return new ArrayList<>(items);
    }

    public List<Member> getMembers() {
        return new ArrayList<>(members);
    }

    public List<Loan> getLoans() {
        return new ArrayList<>(loans);
    }

    private LibraryItem findItem(int id) {
        for (LibraryItem item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    private Member findMember(int id) {
        for (Member member : members) {
            if (member.getMemberId() == id) {
                return member;
            }
        }
        return null;
    }

    private LibraryItem getItemOrThrow(int id) {
        LibraryItem item = findItem(id);
        if (item == null) {
            throw new IllegalArgumentException("No item with id " + id);
        }
        return item;
    }

    private Member getMemberOrThrow(int id) {
        Member member = findMember(id);
        if (member == null) {
            throw new IllegalArgumentException("No member with id " + id);
        }
        return member;
    }

    private Loan findActiveLoan(Member member, LibraryItem item) {
        for (Loan loan : loans) {
            if (!loan.isReturned() && loan.getMember() == member && loan.getItem() == item) {
                return loan;
            }
        }
        return null;
    }
}