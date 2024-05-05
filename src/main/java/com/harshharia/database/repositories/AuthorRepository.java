package com.harshharia.database.repositories;

import com.harshharia.database.domain.entities.AuthorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {
    // here long in 'CrudRepository<AuthorEntity, Long>' is bcz the primary key or id of this-
    // entity is of type Long. so for the books entity it will be string.

    Iterable<AuthorEntity> ageLessThan(int age);

    @Query("SELECT a FROM AuthorEntity a WHERE a.age>?1") //HQL
    Iterable<AuthorEntity> findAuthorWithAgeGreaterThan(int age);
}
