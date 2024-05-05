package com.harshharia.database.controllers;

import com.harshharia.database.domain.dto.BookDto;
import com.harshharia.database.domain.entities.BookEntity;

import com.harshharia.database.mappers.Mapper;
import com.harshharia.database.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private BookService bookService;

    private Mapper<BookEntity, BookDto> bookMapper;

    public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn,@RequestBody BookDto bookDto) {

        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity createdBook = bookService.createBook(isbn, bookEntity);
        BookDto createdBookDto = bookMapper.mapTo(createdBook);
        return new ResponseEntity<>(createdBookDto, HttpStatus.CREATED);

    }

    @GetMapping("/books")
    public List<BookDto> listBooks() {
        List<BookEntity> books = bookService.findAll();
        return books.stream()
                .map(bookMapper::mapTo)
                .collect(Collectors.toList());
    }

}
