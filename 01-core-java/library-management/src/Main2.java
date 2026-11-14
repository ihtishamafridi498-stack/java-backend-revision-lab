import model.*;
import service.Library;

import java.util.List;
import java.util.Scanner;

public class Main2 {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Library library = new Library();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Choose: ");

            try {
                switch (choice) {
                    case 1 -> addItem();
                    case 2 -> addMember();
                    case 3 -> borrow();
                    case 4 -> giveBack();
                    case 5 -> search();
                    case 6 -> printList(library.listAvailableItems());
                    case 7 -> printList(library.listOverdueLoans());
                    case 0 -> {
                        running = false;
                        System.out.println("Goodbye!");
                    }
                    default -> System.out.println("Invalid choice, try again.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ---------- Menu actions ----------

    private static void addItem() {
        System.out.println("Type: 1) Book  2) Magazine  3) DVD");
        int type = readInt("Type: ");
        int id = readInt("Item id: ");
        String title = readText("Title: ");

        switch (type) {
            case 1 -> {
                String author = readText("Author: ");
                String isbn = readText("ISBN: ");
                library.addItem(new Book(id, title, author, isbn));
            }
            case 2 -> {
                int issue = readInt("Issue number: ");
                library.addItem(new Magazine(id, title, issue));
            }
            case 3 -> {
                int minutes = readInt("Duration (minutes): ");
                library.addItem(new DVD(id, title, minutes));
            }
            default -> {
                System.out.println("Unknown item type.");
                return;
            }
        }
        System.out.println("Item added.");
    }

    private static void addMember() {
        System.out.println("Type: 1) Student  2) Teacher");
        int type = readInt("Type: ");
        int id = readInt("Member id: ");
        String name = readText("Name: ");

        switch (type) {
            case 1 -> library.addMember(new StudentMember(id, name));
            case 2 -> library.addMember(new TeacherMember(id, name));
            default -> {
                System.out.println("Unknown member type.");
                return;
            }
        }
        System.out.println("Member added.");
    }

    private static void borrow() {
        int memberId = readInt("Member id: ");
        int itemId = readInt("Item id: ");
        Loan loan = library.borrowItem(memberId, itemId);
        System.out.println("Borrowed! Due on " + loan.getDueDate());
    }

    private static void giveBack() {
        int memberId = readInt("Member id: ");
        int itemId = readInt("Item id: ");
        double fine = library.returnItem(memberId, itemId);
        if (fine > 0) {
            System.out.println("Returned late. Fine: Rs " + fine);
        } else {
            System.out.println("Returned on time. No fine.");
        }
    }

    private static void search() {
        String keyword = readText("Keyword: ");
        printList(library.search(keyword));
    }

    // ---------- Helpers ----------

    private static void printMenu() {
        System.out.println("\n===== LIBRARY MENU =====");
        System.out.println("1. Add item");
        System.out.println("2. Add member");
        System.out.println("3. Borrow item");
        System.out.println("4. Return item");
        System.out.println("5. Search");
        System.out.println("6. List available items");
        System.out.println("7. List overdue loans");
        System.out.println("0. Exit");
    }

    private static void printList(List<?> list) {
        if (list.isEmpty()) {
            System.out.println("Nothing to show.");
            return;
        }
        for (Object o : list) {
            System.out.println(o);
        }
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }
}