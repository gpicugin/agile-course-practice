package ru.unn.agile.BookSale.Model;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.Set;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BooksTableTest {
    @BeforeClass
    public static void setUpBooks() {
        books = new HashSet<Book>();
        books.add(new Book("Harry Potter and the Philosopher's Stone", 8.0, UUID.randomUUID()));
        books.add(new Book("Harry Potter And The Chamber of secrets", 8.0, UUID.randomUUID()));
        books.add(new Book("Harry Potter and the prisoner of Azkaban", 8.0, UUID.randomUUID()));
        books.add(new Book("Harry Potter and the Goblet of Fire", 8.0, UUID.randomUUID()));
        books.add(new Book("Harry Potter and the Order of the Phoenix", 8.0, UUID.randomUUID()));
    }

    @AfterClass
    public static void cleanAll() {
        books = null;
    }

    @Test
    public void canCreateEmptyBooksTable() {
        BooksTable booksTable = new BooksTable();

        assertNotNull(booksTable);
    }

    @Test
    public void canCreateNotEmptyBooksTable() {
        BooksTable booksTable = new BooksTable(books);

        assertNotNull(booksTable);
    }

    @Test
    public void canGetBooks() {
        BooksTable booksTable = new BooksTable(books);

        assertNotNull(booksTable.getBooks());
    }

    @Test
    public void isSizeOfBooksCorrectInNotEmptyBooksTable() {
        BooksTable booksTable = new BooksTable(books);

        assertEquals(5, booksTable.getBooks().size());
    }

    @Test
    public void canGetBookById() {
        BooksTable booksTable = new BooksTable(books);
        Book searchBook = (Book) books.toArray()[0];

        assertEquals(booksTable.getBookById(searchBook.getId()), searchBook);
    }

    private static Set<Book> books;
}
