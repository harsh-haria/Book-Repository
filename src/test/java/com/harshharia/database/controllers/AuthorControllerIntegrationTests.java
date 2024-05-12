package com.harshharia.database.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harshharia.database.TestDataUtil;
import com.harshharia.database.domain.dto.AuthorDto;
import com.harshharia.database.domain.entities.AuthorEntity;
import com.harshharia.database.mappers.impl.AuthorMapperImpl;
import com.harshharia.database.repositories.AuthorRepository;
import com.harshharia.database.services.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.print.attribute.standard.Media;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {

    private AuthorService authorService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
    @Autowired
    private AuthorMapperImpl authorMapperImpl;
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.authorService = authorService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthorA();
        testAuthor.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthorA();
        testAuthor.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Harsh Haria")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(24)
        );
    }

    @Test
    public void testThatListAuthorsReturnsHttpStatus200() throws  Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListAuthorsReturnsListOfAuthors() throws Exception {
        AuthorEntity authorA = TestDataUtil.createTestAuthorA();
        authorService.save(authorA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("Harsh Haria")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(24)
        );
    }

    @Test
    public void testThatGetAuthorsReturnsHttpStatus200() throws  Exception {
        AuthorEntity authorA = TestDataUtil.createTestAuthorA();
        authorService.save(authorA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + authorA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetAuthorsReturnsHttpStatus404() throws  Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + 999)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetAuthorsReturnsAuthorWhenAuthorExists() throws Exception {
        AuthorEntity authorA = TestDataUtil.createTestAuthorA();
        authorService.save(authorA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + authorA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Harsh Haria")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(24)
        );
    }

    @Test
    public void testThatFullUpdateAuthorReturns404WhenAuthorDoesNotExist() throws Exception {
        AuthorEntity authorA = TestDataUtil.createTestAuthorA();
        String testDto = objectMapper.writeValueAsString(authorA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testDto)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus200WhenAuthorExists() throws Exception {
        AuthorEntity authorA = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthor = authorService.save(authorA);
        AuthorDto authorDto = authorMapperImpl.mapTo(savedAuthor);
        String authorJsonString = objectMapper.writeValueAsString(authorDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/"+savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJsonString)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdateUpdatesExistingAuthor() throws Exception {
        AuthorEntity authorA = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthor = authorService.save(authorA);

        AuthorEntity testAuthorB = TestDataUtil.createTestAuthorB();
        testAuthorB.setId(savedAuthor.getId());

        String authorDtoUpdateJson = objectMapper.writeValueAsString(testAuthorB);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/"+savedAuthor.getId())
                .contentType(MediaType.APPLICATION_JSON
                ).content(authorDtoUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthorB.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthorB.getAge())
        );
    }

    @Test
    public void testThatPartialUpdateExistingAuthorReturnsHttpStatus200() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        AuthorEntity testAuthorB = TestDataUtil.createTestAuthorB();
        authorRepository.save(testAuthorA);

        AuthorDto authorDto = authorMapperImpl.mapTo(testAuthorA);
        authorDto.setName(testAuthorB.getName());
        authorDto.setAge(testAuthorB.getAge());

        String jsonAuthorDto = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/"+testAuthorA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthorDto)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateExistingAuthorReturnsUpatedAuthor() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        AuthorEntity testAuthorB = TestDataUtil.createTestAuthorB();
        authorRepository.save(testAuthorA);

        AuthorDto authorDto = authorMapperImpl.mapTo(testAuthorA);
        authorDto.setName(testAuthorB.getName());
        authorDto.setAge(testAuthorB.getAge());

        String jsonAuthorDto = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/"+testAuthorA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthorDto)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(testAuthorA.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(authorDto.getAge())
        );
    }

    @Test
    public void testThatDeleteAuthorReturnsHttpStatus204WhenAuthorDoesntExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/9999")
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeleteAuthorReturnsHttpStatus204WhenAuthorExists() throws Exception {
        AuthorEntity authorA = TestDataUtil.createTestAuthorA();
        authorService.save(authorA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/"+authorA.getId())
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

}

