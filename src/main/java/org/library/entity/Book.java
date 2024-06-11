package org.library.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Book {
    @JsonProperty("autor")
    private String author;

    @JsonProperty("titulo")
    private String title;

    @JsonProperty("genero")
    private String genre;

    @JsonProperty("exemplares")
    private Integer copies;

    // Default constructor is necessary for Jackson
    public Book() {}

    // Constructor
    public Book(String author, String title, String genre, Integer copies) {
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.copies = copies;
    }

    // Getters and Setters
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public Integer getCopies() { return copies; }
    public void setCopies(Integer copies) { this.copies = copies; }

    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
