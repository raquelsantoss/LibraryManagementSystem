package org.library.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.library.resource.JsonUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookCollection {
    @JsonProperty("livros")
    private List<Book> books = new ArrayList<>();

    // Getters and Setters
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(String filePath, Book book) throws IOException {
        this.books.add(book);
        JsonUtil.writeJsonFile(filePath ,this);
    }

    public void removeBook(String filePath, Book book) throws IOException {
        this.books.remove(book);
        JsonUtil.writeJsonFile(filePath ,this);
    }

    public void updateBookCopies(String filePath, String title, Integer newCopies) throws IOException {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                book.setCopies(newCopies);
                JsonUtil.writeJsonFile(filePath, this);
                return;
            }
        }
        throw new IOException("Book with title " + title + " not found.");
    }

    public String booksString() {
        StringBuilder booksString = new StringBuilder();
        for (Book book : books) {
            booksString.append(book.toString());
        }
        return booksString.toString();
    }

    @Override
    public String toString() {
        return "BookCollection{" +
                "books=" + books +
                '}';
    }
}
