package model;

import contract.Borrowable;
import contract.Searchable;

public abstract class LibraryItem implements Borrowable, Searchable {
    private final int id;
    private final String title;
    private boolean available;

    public LibraryItem(int id, String title) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be greater than zero");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        this.id = id;
        this.title = title;
        this.available = true;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    public abstract int getLoanDays();

    public abstract String getItemType();

    @Override
    public void markBorrowed() {
        if (!available) {
            throw new IllegalStateException("Item '" + title + "' is already borrowed");
        }
        available = false;
    }

    @Override
    public void markReturned() {
        if (available) {
            throw new IllegalStateException("Item '" + title + "' was not borrowed");
        }
        available = true;
    }

    @Override
    public boolean matches(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return false;
        }
        return title.toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public String toString() {
        return getItemType() + "{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", available=" + available +
                '}';
    }
}