package com.harshharia.database;

import com.harshharia.database.domain.dto.AuthorDto;
import com.harshharia.database.domain.dto.BookDto;
import com.harshharia.database.domain.entities.AuthorEntity;
import com.harshharia.database.domain.entities.BookEntity;

public final class TestDataUtil {
    public TestDataUtil() {}


    public static AuthorEntity createTestAuthorA() {
        return new AuthorEntity(1L, "Harsh Haria", 24);
    }

    public static AuthorEntity createTestAuthorB() {
        return new AuthorEntity(2L, "John K.", 55);
    }

    public static AuthorEntity createTestAuthorC() {
        return new AuthorEntity(3L, "Ryan Reynolds", 38);
    }

//    public static BookEntity createTestBookA(AuthorEntity authorEntity) {
//        return new BookEntity("A1", "Code never lies", authorEntity);
//    }
    public static BookEntity createTestBookA(AuthorEntity authorEntity){
        return BookEntity.builder().isbn("A1").title("Code never lies").authorEntity(authorEntity).build();
    }

    public static BookEntity createTestBookB(AuthorEntity authorEntity) {
        return new BookEntity("A2", "Code never lies", authorEntity);
    }

    public static BookEntity createTestBookC(AuthorEntity authorEntity) {
        return new BookEntity("A3", "Code never lies", authorEntity);
    }

    public static BookDto createTestBookDtoA(AuthorDto author) {
        return BookDto.builder().isbn("A4").title("Quantumania").author(author).build();
    }
}
