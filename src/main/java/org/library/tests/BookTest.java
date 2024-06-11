package org.library.tests;


import org.junit.Test;
import org.library.entity.Book;
import static org.testng.Assert.*;

public class BookTest {

    @Test
    public void testBookGettersAndSetters() {
        Book book = new Book();
        book.setAuthor("Jane Austen");
        book.setTitle("Pride and Prejudice");
        book.setGenre("Romance");
        book.setCopies(5);

        assertEquals("Jane Austen", book.getAuthor());
        assertEquals("Pride and Prejudice", book.getTitle());
        assertEquals("Romance", book.getGenre());
        assertEquals(5, book.getCopies());

    }

    @Test
    public void testBookConstructor() {
        Book book = new Book("Jane Austen", "Pride and Prejudice", "Romance", 5);

        assertEquals("Jane Austen", book.getAuthor());
        assertEquals("Pride and Prejudice", book.getTitle());
        assertEquals("Romance", book.getGenre());
        assertEquals(5, book.getCopies());

    }
}
