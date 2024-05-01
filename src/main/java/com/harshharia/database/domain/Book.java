package com.harshharia.database.domain;

public class Book {
    private String isbn;
    private String title;
    private Long author_id;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Long author_id) {
        this.author_id = author_id;
    }

    public Book(String isbn, String title, Long author_id) {
        this.isbn = isbn;
        this.title = title;
        this.author_id = author_id;
    }

    @Override
    public String toString() {
        return "Book{" +
                "author_id=" + author_id +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}
