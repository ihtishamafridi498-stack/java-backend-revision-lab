package contract;

public interface Fineable {
    double calculateFine();

    default boolean hasFine() {
        return calculateFine() > 0;
    }
}