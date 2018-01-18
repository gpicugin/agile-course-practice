package ru.unn.agile.BookSale.Model;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class BookTest {

    @Test
    public void canCreateBook() {
        Book book = new Book("Harry Potter and the Philosopher's Stone", 8.0, UUID.randomUUID());

        assertNotNull(book);
    }

    @Test
    public void canGetBookName() {
        Book book = new Book("Harry Potter And The Chamber of secrets", 8.0, UUID.randomUUID());

        assertEquals("Harry Potter And The Chamber of secrets", book.getName());
    }

    @Test
    public void canGetBookPrice() {
        Book book = new Book("Harry Potter and the prisoner of Azkaban", 8.0, UUID.randomUUID());

        assertEquals(8.0, book.getPrice(), delta);
    }

    @Test
    public void canGetBookId() {
        UUID bookId = UUID.randomUUID();
        Book book = new Book("Harry Potter and the Deathly Hallows", 8.0, bookId);

        assertEquals(bookId, book.getId());
    }

    private final double delta = 1e-6;
}
