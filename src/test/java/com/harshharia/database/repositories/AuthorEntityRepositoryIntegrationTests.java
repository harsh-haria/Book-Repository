package com.harshharia.database.repositories;

import com.harshharia.database.TestDataUtil;
import com.harshharia.database.domain.entities.AuthorEntity;
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
// every time a new test function is being run. // if we have declared a authorEntity creation in one function.
// that will affect the other test functions if we don't clear the databases.
public class AuthorEntityRepositoryIntegrationTests {

    private AuthorRepository underTest;

    @Autowired
    public AuthorEntityRepositoryIntegrationTests(AuthorRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        underTest.save(authorEntity);
        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntity);
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorA();
        underTest.save(authorEntityA);
        AuthorEntity authorEntityB = TestDataUtil.createTestAuthorB();
        underTest.save(authorEntityB);
        AuthorEntity authorEntityC = TestDataUtil.createTestAuthorC();
        underTest.save(authorEntityC);

        Iterable<AuthorEntity> result = underTest.findAll();
        assertThat(result).hasSize(3).containsExactly(authorEntityA, authorEntityB, authorEntityC);
    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorA();
        underTest.save(authorEntityA);
        authorEntityA.setName("UPDATED");
        underTest.save(authorEntityA);
        Optional<AuthorEntity> result = underTest.findById(authorEntityA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntityA);
    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorA();
        underTest.save(authorEntityA);
        underTest.deleteById(authorEntityA.getId());
        Optional<AuthorEntity> result = underTest.findById(authorEntityA.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testThatGetAuthorsWithAgeLessthan() {
        AuthorEntity testauthorA = TestDataUtil.createTestAuthorA();
        underTest.save(testauthorA);
        AuthorEntity testauthorB = TestDataUtil.createTestAuthorB();
        underTest.save(testauthorB);
        AuthorEntity testauthorC = TestDataUtil.createTestAuthorC();
        underTest.save(testauthorC);

        Iterable<AuthorEntity> result = underTest.ageLessThan(50);
        assertThat(result).containsExactly(testauthorA, testauthorC);
    }

    @Test
    public void testThatGetAuthorsWithAgeGreaterThan() {
        AuthorEntity testauthorA = TestDataUtil.createTestAuthorA();
        underTest.save(testauthorA);
        AuthorEntity testauthorB = TestDataUtil.createTestAuthorB();
        underTest.save(testauthorB);
        AuthorEntity testauthorC = TestDataUtil.createTestAuthorC();
        underTest.save(testauthorC);

        Iterable<AuthorEntity> result = underTest.findAuthorWithAgeGreaterThan(50);
        assertThat(result).containsExactly(testauthorB);
    }
}
