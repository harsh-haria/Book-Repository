package com.harshharia.database.controllers;

import com.harshharia.database.domain.dto.BookDto;
import com.harshharia.database.domain.entities.BookEntity;

import com.harshharia.database.mappers.Mapper;
import com.harshharia.database.repositories.BookRepository;
import com.harshharia.database.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private final BookRepository bookRepository;
    private BookService bookService;

    private Mapper<BookEntity, BookDto> bookMapper;

    public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService, BookRepository bookRepository) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) throws Exception {
            Boolean bookExists = bookService.doesExists(isbn);
            BookEntity bookEntity = bookMapper.mapFrom(bookDto);
            BookEntity createdBook = bookService.createUpdateBook(isbn, bookEntity);
            BookDto createdBookDto = bookMapper.mapTo(createdBook);
        if (bookExists) {
            // UPDATE
            return new ResponseEntity<>(createdBookDto, HttpStatus.OK);
        } else {
            // CREATE
            return new ResponseEntity<>(createdBookDto, HttpStatus.CREATED);
        }


    }

    @GetMapping("/books")
    public List<BookDto> listBooks() {
        List<BookEntity> books = bookService.findAll();
        return books.stream()
                .map(bookMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn) {
        Optional<BookEntity> bookDto = bookService.getBook(isbn);
        return bookDto.map(bookEntity -> {
            BookDto bookDto1 = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto1, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/books/{isbn}")
    public ResponseEntity<BookDto> updateBook(
            @PathVariable("isbn") String isbn, @RequestBody BookDto bookDto
    ) {
        if (!bookService.doesExists(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity updatedBookEntity = bookService.partialUpdate(isbn, bookEntity);
        BookDto returnBookDto = bookMapper.mapTo(updatedBookEntity);
        return new ResponseEntity<>(returnBookDto, HttpStatus.OK);
    }

}
