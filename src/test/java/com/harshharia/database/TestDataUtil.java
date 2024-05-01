package com.harshharia.database;

import com.harshharia.database.domain.Author;
import com.harshharia.database.domain.Book;

public final class TestDataUtil {
    public TestDataUtil() {}


    public static Author createTestAuthorA() {
        return new Author(1L, "Harsh Haria", 24);
    }

    public static Author createTestAuthorB() {
        return new Author(2L, "Harsh n Haria", 24);
    }

    public static Author createTestAuthorC() {
        return new Author(3L, "Harsh N Haria", 24);
    }

    public static Book createTestBookA() {
        return new Book("A1", "Code never lies", 1L);
    }

    public static Book createTestBookB() {
        return new Book("A2", "Code never lies", 1L);
    }

    public static Book createTestBookC() {
        return new Book("A3", "Code never lies", 1L);
    }
}
