package ru.unn.agile.BookSale.Model;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Order {
    public Order() {
        this.booksList = new HashMap<UUID, Integer>();
        this.booksTable = new BooksTable();
    }

    public Order(final BooksTable booksTable) {
        this.booksList = new HashMap<UUID, Integer>();
        this.booksTable = booksTable;
    }

    public Order(final Map<UUID, Integer> booksList, final BooksTable booksTable) {
        this.booksList = booksList;
        this.booksTable = booksTable;
    }

    public Map<UUID, Integer> getBooksList() {
        return this.booksList;
    }

    public void addBook(final UUID bookId) {
        this.booksList.put(bookId, this.booksList.getOrDefault(bookId, 0) + 1);
    }

    public void deleteBook(final UUID bookId) {
        if (!this.booksList.containsKey(bookId)) {
            throw new InvalidParameterException("This book is not in order");
        }

        final int bookValue = this.booksList.get(bookId);
        if (bookValue > 1) {
            this.booksList.put(bookId, bookValue - 1);
        } else {
            this.booksList.remove(bookId);
        }
    }

    public double getMaxDiscount() {
        return AMOUNT_OF_DISCOUNT[this.booksList.size()];
    }

    public double getCost() {
        double cost = 0.0;
        final double discount = this.getMaxDiscount();
        for (Map.Entry<UUID, Integer> book : this.booksList.entrySet()) {
            cost += this.getBookPrice(book.getKey()) * (book.getValue() - discount);
        }
        return cost;
    }

    public double getBookPrice(final UUID bookId) {
        return booksTable.getBookById(bookId).getPrice();
    }

    private static final double[] AMOUNT_OF_DISCOUNT = {0.0, 0.0, 0.05, 0.1, 0.2, 0.25};
    private Map<UUID, Integer> booksList;
    private BooksTable booksTable;
}
