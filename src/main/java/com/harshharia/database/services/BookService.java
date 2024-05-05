package com.harshharia.database.services;

import com.harshharia.database.domain.entities.BookEntity;

import java.util.List;

public interface BookService {
    BookEntity createBook(String isbn, BookEntity book);

    List<BookEntity> findAll();
}
