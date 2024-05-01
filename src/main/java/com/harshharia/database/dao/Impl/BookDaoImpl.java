package com.harshharia.database.dao.Impl;

import com.harshharia.database.dao.BookDao;
import com.harshharia.database.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class BookDaoImpl implements BookDao {

    private final JdbcTemplate jdbcTemplate;

    public BookDaoImpl (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Book book) {
        String sql = "INSERT INTO book (isbn, title, author_id) VALUES (?, ?, ?)";
        String isbn = book.getIsbn();
        String title = book.getTitle();
        Long authorId = book.getAuthor_id();
        jdbcTemplate.update(sql, isbn, title, authorId);
    }

    @Override
    public Optional<Book> findOne(String isbn) {
        String sql = "SELECT * FROM book WHERE isbn = ? LIMIT 1";
        List<Book> book = jdbcTemplate.query(sql, new BookRowMapper(), isbn);
        Optional<Book> bookData = book.stream().findFirst();
        return bookData;
    }

    public static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book(
                rs.getString("isbn"),
                rs.getString("title"),
                rs.getLong("author_id")
            );
        }
    }

    @Override
    public List<Book> find() {
        String sql = "SELECT * FROM book";
        List<Book> response = jdbcTemplate.query(sql, new BookRowMapper());
        return response;
    }

    @Override
    public void update(String isbn, Book book) {
        String sql = "UPDATE book SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?";
        String newIsbn = book.getIsbn();
        String title = book.getTitle();
        Long authorId = book.getAuthor_id();
        jdbcTemplate.update(sql, newIsbn, title, authorId, isbn);
    }

    @Override
    public void delete(String isbn) {
        String sql = "DELETE FROM book WHERE book.isbn = ?";
        jdbcTemplate.update(sql, isbn);
    }
}
