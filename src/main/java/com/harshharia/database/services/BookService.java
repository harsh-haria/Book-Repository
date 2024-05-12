package com.harshharia.database.services;

import com.harshharia.database.domain.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {
    BookEntity createUpdateBook(String isbn, BookEntity book);

    List<BookEntity> findAll();

    Page<BookEntity> findAllPageable(Pageable pageable);

    Optional<BookEntity> getBook(String isbn);

    Boolean doesExists(String isbn);

    BookEntity partialUpdate(String isbn, BookEntity bookEntity);

    void delete(String isbn);
}
