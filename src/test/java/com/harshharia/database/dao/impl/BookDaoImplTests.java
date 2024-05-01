package com.harshharia.database.dao.impl;

import com.harshharia.database.TestDataUtil;
import com.harshharia.database.dao.Impl.BookDaoImpl;
import com.harshharia.database.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl underTest;

    @Test
    public void testThatCreateBookGeneratesCorrectSql(){
       Book book = TestDataUtil.createTestBookA();

        underTest.create(book);

        String sql = "INSERT INTO book (isbn, title, author_id) VALUES (?, ?, ?)";
        String isbn = book.getIsbn();
        String title = book.getTitle();
        Long authorId = book.getAuthor_id();
        verify(jdbcTemplate).update(
                eq(sql), eq(isbn), eq(title), eq(authorId)
        );
    }

    @Test
    public void testThatFindOneBookGeneratesCorrectSql() {

        underTest.findOne("A1");

        String isbn = "A1";
        String sql = "SELECT * FROM book WHERE isbn = ? LIMIT 1";
        verify(jdbcTemplate).query(
                eq(sql),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
                eq(isbn)
        );
    }

    @Test
    public void testThatFindGeneratesCorrectSql () {
        underTest.find();
        verify(jdbcTemplate).query(
                eq("SELECT * FROM books"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any()
        );
    }

    @Test
    public void testThatUpdateGeneratesCorrectSql() {
        Book book = TestDataUtil.createTestBookA();
        underTest.update(book.getIsbn(), book);

        verify(jdbcTemplate).update(
                "UPDATE book SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?",
                book.getIsbn(), book.getTitle(), book.getAuthor_id(), book.getIsbn()
        );
    }

    @Test
    public void testThatDeleteGeneratesTheCorrectSql(){
        Book book = TestDataUtil.createTestBookA();
        underTest.delete(book.getIsbn());
        verify(jdbcTemplate).update(
                "DELETE FROM book WHERE book.isbn = ?", book.getIsbn()
        );
    }

}
