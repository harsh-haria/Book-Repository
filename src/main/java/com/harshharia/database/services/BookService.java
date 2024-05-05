package com.harshharia.database.services;

import com.harshharia.database.domain.entities.BookEntity;

public interface BookService {
    BookEntity createBook(String isbn, BookEntity book);
}
