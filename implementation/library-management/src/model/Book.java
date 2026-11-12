package model;

public class Book extends LibraryItem{

    private String author;
    private String isbn;

    public Book(int id, String title, String author, String isbn) {
        super(id, title);
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Author cannot be empty");
        }
        this.author = author;
        this.isbn = isbn;
    }

    public Book(int id, String title, String author) {
        this(id, title, author, "N/A");
    }


    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }


    @Override
    public int getLoanDays() {
        return 14;
    }

    @Override
    public String getItemType() {
        return "Book";
    }

    @Override
    public String toString() {
        return super.toString() + " [author=" + author + ", isbn=" + isbn + "]";
    }

    @Override
    public boolean matches(String keyword) {
        if (super.matches(keyword)) {
            return true;
        }
        if (keyword == null || keyword.isBlank()) {
            return false;
        }
        String k = keyword.toLowerCase();
        return author.toLowerCase().contains(k) || isbn.toLowerCase().contains(k);
    }
}
