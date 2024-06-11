package org.library.tests;

import org.junit.Test;
import org.library.entity.Book;
import org.library.entity.BookCollection;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class BookCollectionTest {


    private static final String TEMP_FILE = "temp_books.json";
    private static final String RESOURCES_DIR = "src/main/java/org/library/resource/";

    @Test
    public void testSetBooks() {
        Book book1 = new Book("Jane Austen", "Pride and Prejudice", "Romance",5);
        Book book2 = new Book("George Orwell", "1984", "Dystopian",5);

        List<Book> books = Arrays.asList(book1, book2);

        BookCollection bookCollection = new BookCollection();
        bookCollection.setBooks(books);

        assertEquals(2, bookCollection.getBooks().size(), "Book collection should contain 2 books.");
        assertEquals("Jane Austen", bookCollection.getBooks().get(0).getAuthor(), "First book's author should be Jane Austen.");
        assertEquals("George Orwell", bookCollection.getBooks().get(1).getAuthor(), "Second book's author should be George Orwell.");
    }

    @Test
    public void testAddBook() throws IOException {
        Book book1 = new Book("Jane Austen", "Pride and Prejudice", "Romance", 5);
        BookCollection bookCollection = new BookCollection();
        bookCollection.setBooks(new ArrayList<>(List.of(book1)));

        Book book2 = new Book("George Orwell", "1984", "Dystopian", 5);
        bookCollection.addBook(RESOURCES_DIR+TEMP_FILE, book2);

        assertEquals(2, bookCollection.getBooks().size(), "Book collection should contain 2 books after adding.");
        assertTrue(bookCollection.getBooks().contains(book2), "Book collection should contain '1984' by George Orwell.");
    }

    @Test
    public void testRemoveBook() throws IOException {
        Book book1 = new Book("Jane Austen", "Pride and Prejudice", "Romance", 5);
        Book book2 = new Book("George Orwell", "1984", "Dystopian", 5);

        List<Book> books = new ArrayList<>(Arrays.asList(book1, book2));

        BookCollection bookCollection = new BookCollection();
        bookCollection.setBooks(books);

        bookCollection.removeBook(RESOURCES_DIR+TEMP_FILE, book1);

        assertEquals(1, bookCollection.getBooks().size(), "Book collection should contain 1 book after removal.");
        assertTrue(bookCollection.getBooks().contains(book2), "Book collection should still contain '1984' by George Orwell.");
        assertFalse(bookCollection.getBooks().contains(book1), "Book collection should not contain 'Pride and Prejudice' by Jane Austen.");
    }

    @Test
    public void testRemoveBookFromEmptyCollection() throws IOException {
        Book book1 = new Book("Jane Austen", "Pride and Prejudice", "Romance", 5);
        BookCollection bookCollection = new BookCollection();
        bookCollection.setBooks(new ArrayList<>());

        bookCollection.removeBook(RESOURCES_DIR+TEMP_FILE, book1);

        assertEquals(0, bookCollection.getBooks().size(), "Book collection should still be empty after trying to remove a book from an empty collection.");
    }

    @Test
    public void testAddBookToEmptyCollection() throws IOException {
        Book book1 = new Book("Jane Austen", "Pride and Prejudice", "Romance", 5);
        BookCollection bookCollection = new BookCollection();
        bookCollection.setBooks(new ArrayList<>());

        bookCollection.addBook(RESOURCES_DIR+TEMP_FILE, book1);

        assertEquals(1, bookCollection.getBooks().size(), "Book collection should contain 1 book after adding to an empty collection.");
        assertTrue(bookCollection.getBooks().contains(book1), "Book collection should contain 'Pride and Prejudice' by Jane Austen.");
    }
}
