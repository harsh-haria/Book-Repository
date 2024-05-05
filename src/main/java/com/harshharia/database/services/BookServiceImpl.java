package com.harshharia.database.services;

import com.harshharia.database.domain.entities.AuthorEntity;
import com.harshharia.database.domain.entities.BookEntity;
import com.harshharia.database.repositories.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService{

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createBook(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }
}
