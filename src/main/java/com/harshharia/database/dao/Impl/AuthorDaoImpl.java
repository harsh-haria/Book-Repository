package com.harshharia.database.dao.Impl;

import com.harshharia.database.dao.AuthorDao;
import com.harshharia.database.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final JdbcTemplate jdbcTemplate;

    public AuthorDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Author author) {
        String sql = "INSERT INTO authors (id, name, age) VALUES (?, ?, ?)";
        Long id = author.getId();
        String name = author.getName();
        int age = author.getAge();
        this.jdbcTemplate.update(sql, id, name, age);
    }

    @Override
    public Optional<Author> findOne(long l) {
        String sql = "SELECT * FROM authors WHERE id = ? LIMIT 1";
        List<Author> result = jdbcTemplate.query(sql, new AuthorMapper(), l);
        Optional<Author> authorData = result.stream().findFirst();
        return authorData;
    }

    @Override
    public List<Author> find() {
        String sql = "SELECT * FROM authors";
        List<Author> authorList = jdbcTemplate.query(sql, new AuthorMapper());
        return authorList;
    }

    @Override
    public void update(long id, Author author) {
        String sql = "UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?";
        Long ids = author.getId();
        String name = author.getName();
        int age = author.getAge();
        jdbcTemplate.update(sql, ids, name, age, id);
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM authors WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }


    public static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Author(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getInt("age")
            );
        }
    }
}
