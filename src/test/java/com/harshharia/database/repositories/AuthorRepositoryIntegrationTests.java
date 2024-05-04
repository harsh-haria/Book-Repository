package com.harshharia.database.repositories;

import com.harshharia.database.TestDataUtil;
import com.harshharia.database.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
// DirtiesContext: this is so that when we run all the tests the database is cleared
// every time a new test function is being run. // if we have declared a author creation in one function.
// that will affect the other test functions if we don't clear the databases.
public class AuthorRepositoryIntegrationTests {

    private AuthorRepository underTest;

    @Autowired
    public AuthorRepositoryIntegrationTests(AuthorRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthorA();
        underTest.save(author);
        Optional<Author> result = underTest.findById(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
        Author authorA = TestDataUtil.createTestAuthorA();
        underTest.save(authorA);
        Author authorB = TestDataUtil.createTestAuthorB();
        underTest.save(authorB);
        Author authorC = TestDataUtil.createTestAuthorC();
        underTest.save(authorC);

        Iterable<Author> result = underTest.findAll();
        assertThat(result).hasSize(3).containsExactly(authorA, authorB, authorC);
    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        Author authorA = TestDataUtil.createTestAuthorA();
        underTest.save(authorA);
        authorA.setName("UPDATED");
        underTest.save(authorA);
        Optional<Author> result = underTest.findById(authorA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorA);
    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        Author authorA = TestDataUtil.createTestAuthorA();
        underTest.save(authorA);
        underTest.deleteById(authorA.getId());
        Optional<Author> result = underTest.findById(authorA.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testThatGetAuthorsWithAgeLessthan() {
        Author testauthorA = TestDataUtil.createTestAuthorA();
        underTest.save(testauthorA);
        Author testauthorB = TestDataUtil.createTestAuthorB();
        underTest.save(testauthorB);
        Author testauthorC = TestDataUtil.createTestAuthorC();
        underTest.save(testauthorC);

        Iterable<Author> result = underTest.ageLessThan(50);
        assertThat(result).containsExactly(testauthorA, testauthorC);
    }

    @Test
    public void testThatGetAuthorsWithAgeGreaterThan() {
        Author testauthorA = TestDataUtil.createTestAuthorA();
        underTest.save(testauthorA);
        Author testauthorB = TestDataUtil.createTestAuthorB();
        underTest.save(testauthorB);
        Author testauthorC = TestDataUtil.createTestAuthorC();
        underTest.save(testauthorC);

        Iterable<Author> result = underTest.findAuthorWithAgeGreaterThan(50);
        assertThat(result).containsExactly(testauthorB);
    }
}
