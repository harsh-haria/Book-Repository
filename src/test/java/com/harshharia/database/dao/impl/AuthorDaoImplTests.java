package com.harshharia.database.dao.impl;

import com.harshharia.database.TestDataUtil;
import com.harshharia.database.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import com.harshharia.database.dao.Impl.AuthorDaoImpl;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthorDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl underTest;

    @Test
    public void testThatCreateAuthorGeneratesCorrectSql() {
        Author author = TestDataUtil.createTestAuthorA();

        underTest.create(author);

        String sql = "INSERT INTO authors (id, name, age) VALUES (?, ?, ?)";
        Long id = author.getId();
        String name = author.getName();
        int age = author.getAge();

        verify(jdbcTemplate).update(eq(sql), eq(id), eq(name), eq(age));
    }

    @Test
    public void testThatFindOneGeneratesTheCorrectSql() {
        underTest.findOne(1L);
        verify(jdbcTemplate).query(
            eq("SELECT * FROM authors WHERE id = ? LIMIT 1"),
            ArgumentMatchers.<AuthorDaoImpl.AuthorMapper>any(),
            eq(1L)
        );
    }

    @Test
    public void testThatFindManyGeneratesTheCorrectSql() {
        underTest.find();
        verify(jdbcTemplate).query(
                eq("SELECT * FROM authors"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorMapper>any()
        );
    }

    @Test
    public void testThatUpdateGeneratesTheCorrectSql() {
        Author author = TestDataUtil.createTestAuthorA();
        underTest.update(3L, author);
        verify(jdbcTemplate).update(
                "UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?",
                1L, "Harsh Haria", 24, 3L
        );
    }

    @Test
    public void testThatDeleteGeneratesTheCorrectSql() {
        Author author = TestDataUtil.createTestAuthorA();
        underTest.delete(1L);
        verify(jdbcTemplate).update(
                "DELETE FROM authors WHERE id = ?",
                author.getId()
        );
    }
}
