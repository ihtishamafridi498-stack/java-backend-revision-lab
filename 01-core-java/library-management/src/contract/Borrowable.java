package contract;

public interface Borrowable {
    void markBorrowed();
    void markReturned();
    boolean isAvailable();
}