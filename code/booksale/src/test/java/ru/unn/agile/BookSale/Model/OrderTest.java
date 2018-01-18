package ru.unn.agile.BookSale.Model;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.UUID;

import java.security.InvalidParameterException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class OrderTest {
    @BeforeClass
    public static void setUpBooks() {
        book1 = new Book("Harry Potter and the Philosopher's Stone", 8.0, UUID.randomUUID());
        book2 = new Book("Harry Potter and the Half-Blood Prince", 8.0, UUID.randomUUID());
        book3 = new Book("Harry Potter and the prisoner of Azkaban", 8.0, UUID.randomUUID());
        book4 = new Book("Harry Potter and the Order of the Phoenix", 8.0, UUID.randomUUID());
        book5 = new Book("Harry Potter and the Deathly Hallows", 8.0, UUID.randomUUID());

        books = new HashSet<Book>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        booksTable = new BooksTable(books);

        booksList = new HashMap<UUID, Integer>();
        booksList.put(book1.getId(), 1);
        booksList.put(book2.getId(), 2);
        booksList.put(book3.getId(), 3);
    }

    @AfterClass
    public static void cleanAll() {
        book1 = null;
        book2 = null;
        book3 = null;
        book4 = null;
        book5 = null;
        books = null;
        booksTable = null;
        booksList = null;
    }

    @Test
    public void canCreateOrder() {
        Order order = new Order(booksList, booksTable);

        assertNotNull(order);
    }

    @Test
    public void canCreateEmptyOrder() {
        Order order = new Order(booksTable);

        assertEquals(0, order.getBooksList().size());
    }

    @Test
    public void canGetBooksListOfNotEmptyOrder() {
        Order order = new Order(booksList, booksTable);

        assertEquals(booksList, order.getBooksList());
    }

    @Test
    public void canGetBooksListOfEmptyOrder() {
        Order order = new Order(booksTable);

        assertEquals(new HashMap<UUID, Integer>(), order.getBooksList());
    }

    @Test
    public void canAddBookToEmptyOrder() {
        Order order = new Order(booksTable);

        order.addBook(book1.getId());

        assertTrue(order.getBooksList().size() == 1);
    }

    @Test
    public void isBookListCorrectAfterAddBookToEmptyOrder() {
        Order order = new Order(booksTable);
        order.addBook(book1.getId());
        Map<UUID, Integer> booksListToCompare = new HashMap<UUID, Integer>();
        booksListToCompare.put(book1.getId(), 1);

        assertEquals(booksListToCompare, order.getBooksList());
    }

    @Test
    public void canAddNewBookToNotEmptyOrder() {
        Order order = new Order(new HashMap<UUID, Integer>(booksList), booksTable);

        order.addBook(book4.getId());

        assertTrue(order.getBooksList().size() == 4);
    }

    @Test
    public void canAddExistingBookToNotEmptyOrder() {
        Order order = new Order(new HashMap<UUID, Integer>(booksList), booksTable);

        order.addBook(book1.getId());

        assertTrue(order.getBooksList().size() == 3);
    }

    @Test
    public void isCountOfBookCorrectWhenAddExistingBookToNotEmptyOrder() {
        Order order = new Order(new HashMap<UUID, Integer>(booksList), booksTable);

        order.addBook(book1.getId());

        assertTrue(order.getBooksList().get(book1.getId()) == 2);
    }

    @Test
    public void canDeleteBookFromNotEmptyOrder() {
        Order order = new Order(new HashMap<UUID, Integer>(booksList), booksTable);

        order.deleteBook(book1.getId());

        assertTrue(order.getBooksList().size() == 2);
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotDeleteBookFromEmptyOrder() {
        Order order = new Order(booksTable);

        order.deleteBook(book1.getId());
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotDeleteNotExistingBookFromNotEmptyOrder() {
        Order order = new Order(booksList, booksTable);

        order.deleteBook(book4.getId());
    }

    @Test
    public void canGetMaxDiscountOfOrderWithoutDifferentBooks() {
        Order order = new Order(booksTable);
        order.addBook(book1.getId());

        assertEquals(0.0, order.getMaxDiscount(), delta);
    }

    @Test
    public void canGetMaxDiscountOfOrderWithThreeDifferentBooks() {
        Order order = new Order(booksList, booksTable);

        assertEquals(0.1, order.getMaxDiscount(), delta);
    }

    @Test
    public void canGetBookPrice() {
        Order order = new Order(booksList, booksTable);

        assertEquals(8.0, order.getBookPrice(book1.getId()), delta);
    }

    @Test
    public void canCalculateCostOfOrderWithTwoIdenticalBooks() {
        Order order = new Order(booksTable);
        order.addBook(book1.getId());
        order.addBook(book1.getId());

        assertEquals(16.0, order.getCost(), delta);
    }

    @Test
    public void canCalculateCostOfOrderWithDiscount10Persent() {
        Order order = new Order(booksList, booksTable);

        assertEquals(45.6, order.getCost(), delta);
    }

    @Test
    public void canCalculateCostOfOrderWithDiscount25Persent() {
        Order order = new Order(booksTable);
        order.addBook(book1.getId());
        order.addBook(book2.getId());
        order.addBook(book3.getId());
        order.addBook(book4.getId());
        order.addBook(book5.getId());

        assertEquals(30.0, order.getCost(), delta);
    }

    private final double delta = 1e-6;
    private static Book book1;
    private static Book book2;
    private static Book book3;
    private static Book book4;
    private static Book book5;
    private static Set<Book> books;
    private static BooksTable booksTable;
    private static Map<UUID, Integer> booksList;
}
