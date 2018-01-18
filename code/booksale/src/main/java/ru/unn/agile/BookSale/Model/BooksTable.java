package ru.unn.agile.BookSale.Model;

import java.util.UUID;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.Collection;

public class BooksTable {

    public BooksTable() {
        this.books = new HashMap<UUID, Book>();
    }

    public BooksTable(final Set<Book> books) {
        this.books = new HashMap<UUID, Book>();
        for (Book book : books) {
            this.books.put(book.getId(), book);
        }
    }

    public Collection<Book> getBooks() {
        return this.books.values();
    }

    public Book getBookById(final UUID id) {
        return this.books.get(id);
    }

    private Map<UUID, Book> books;
}
