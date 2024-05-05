package com.harshharia.database.services;

import com.harshharia.database.domain.dto.BookDto;
import com.harshharia.database.domain.entities.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookService {
    BookEntity createBook(String isbn, BookEntity book);

    List<BookEntity> findAll();

    Optional<BookEntity> getBook(String isbn);
}