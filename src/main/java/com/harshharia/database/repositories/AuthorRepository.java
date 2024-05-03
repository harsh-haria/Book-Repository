package com.harshharia.database.repositories;

import com.harshharia.database.domain.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    // here long in 'CrudRepository<Author, Long>' is bcz the primary key or id of this-
    // entity is of type Long. so for the books entity it will be string.
}
