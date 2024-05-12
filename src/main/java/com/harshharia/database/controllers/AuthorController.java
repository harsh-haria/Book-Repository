package com.harshharia.database.controllers;

import com.harshharia.database.domain.dto.AuthorDto;
import com.harshharia.database.domain.entities.AuthorEntity;
import com.harshharia.database.mappers.Mapper;
import com.harshharia.database.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AuthorController {

     private AuthorService authorService;

     private Mapper<AuthorEntity, AuthorDto> authorMapper;

     public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
          this.authorService = authorService;
          this.authorMapper = authorMapper;
     }

     @PostMapping(path = "/authors")
     public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {
          AuthorEntity authorEntity = authorMapper.mapFrom(author);
          AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
         return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED) ;
     }

     @GetMapping(path = "/authors")
     public ResponseEntity<List<AuthorDto>> listAuthors() {
          List<AuthorEntity> authors = authorService.findAll();
          List<AuthorDto> authorDtos = authors.stream().map(authorMapper::mapTo).collect(Collectors.toList());
          return new ResponseEntity<>(authorDtos, HttpStatus.OK);
     }

     @GetMapping(path = "/authors/{id}")
     public ResponseEntity<AuthorDto> getAuthor(@PathVariable Long id) {
          Optional<AuthorEntity> foundAuthor = authorService.findOne(id);
          return foundAuthor.map(authorEntity -> {
               AuthorDto authorDto = authorMapper.mapTo(authorEntity);
               return new ResponseEntity<>(authorDto, HttpStatus.OK);
          }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
     }

     @PutMapping("/authors/{id}")
     public ResponseEntity<AuthorDto> fullUpdateAuthor(
             @PathVariable Long id, @RequestBody AuthorDto author
     ) {
          if (!authorService.doesExists(id)) {
               return new ResponseEntity<>(HttpStatus.NOT_FOUND);
          }

          AuthorEntity authorEntity = authorMapper.mapFrom(author);
          authorEntity.setId(id);
          AuthorEntity savedAuthor = authorService.save(authorEntity);
          AuthorDto returnAuthorDto = authorMapper.mapTo(savedAuthor);
          return new ResponseEntity<AuthorDto>(returnAuthorDto, HttpStatus.OK);
     }

     @PatchMapping("/authors/{id}")
     public ResponseEntity<AuthorDto> partialUpdate(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto) {
          if (!authorService.doesExists(id)) {
               return new ResponseEntity<>(HttpStatus.NOT_FOUND);
          }
          AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
          AuthorEntity updatedAuthor = authorService.partialUpdate(id, authorEntity);
          return new ResponseEntity<>(authorMapper.mapTo(updatedAuthor), HttpStatus.OK);
     }

     @DeleteMapping("/authors/{id}")
     public ResponseEntity deltetAuthor(@PathVariable Long id) {
          authorService.delete(id);
          return new ResponseEntity(HttpStatus.NO_CONTENT);
     }
}
