package com.harshharia.database.repositories;

import com.harshharia.database.TestDataUtil;
import com.harshharia.database.domain.entities.AuthorEntity;
import com.harshharia.database.domain.entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookEntityRepositoryIntegrationTests {

    private BookRepository underTest;

    @Autowired
    public BookEntityRepositoryIntegrationTests(BookRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        BookEntity bookEntity = TestDataUtil.createTestBookA(authorEntity);
        //here in the statement above we are passing the authorEntity and it will create the authorEntity automatically
        //we are not creating any authors separately. Thanks to ManyToOne annotation.
        //this is because we are using cascade property for authorEntity in bookEntity.
        //@ManyToOne(cascade = CascadeType.ALL)
        underTest.save(bookEntity);
        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();

        BookEntity bookEntityA = TestDataUtil.createTestBookA(authorEntity);
        underTest.save(bookEntityA);

        BookEntity bookEntityB = TestDataUtil.createTestBookB(authorEntity);
        underTest.save(bookEntityB);

        BookEntity bookEntityC = TestDataUtil.createTestBookC(authorEntity);
        underTest.save(bookEntityC);

        Iterable<BookEntity> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(bookEntityA, bookEntityB, bookEntityC);
    }

    @Test
    public void testThatBookCanBeUpdated() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();

        BookEntity bookEntityA = TestDataUtil.createTestBookA(authorEntity);
        underTest.save(bookEntityA);

        bookEntityA.setTitle("UPDATED TITLE");
        underTest.save(bookEntityA);

        Optional<BookEntity> result = underTest.findById(bookEntityA.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntityA);
    }

    @Test
    public void testThatBookCanBeDeleted() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();

        BookEntity bookEntityA = TestDataUtil.createTestBookA(authorEntity);
        underTest.save(bookEntityA);

        underTest.deleteById(bookEntityA.getIsbn());
        Optional<BookEntity> result = underTest.findById(bookEntityA.getIsbn());
        assertThat(result).isNotPresent();
    }

}
