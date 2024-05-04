package com.harshharia.database;

import com.harshharia.database.domain.Author;
import com.harshharia.database.domain.Book;

public final class TestDataUtil {
    public TestDataUtil() {}


    public static Author createTestAuthorA() {
        return new Author(1L, "Harsh Haria", 24);
    }

    public static Author createTestAuthorB() {
        return new Author(2L, "John K.", 55);
    }

    public static Author createTestAuthorC() {
        return new Author(3L, "Ryan Reynolds", 38);
    }

//    public static Book createTestBookA(Author author) {
//        return new Book("A1", "Code never lies", author);
//    }
    public static Book createTestBookA(Author author){
        return Book.builder().isbn("A1").title("Code never lies").author(author).build();
    }

    public static Book createTestBookB(Author author) {
        return new Book("A2", "Code never lies", author);
    }

    public static Book createTestBookC(Author author) {
        return new Book("A3", "Code never lies", author);
    }
}
