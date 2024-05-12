package com.harshharia.database.services;

import com.harshharia.database.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    AuthorEntity save(AuthorEntity authorEntity);

    List<AuthorEntity> findAll();

    Optional<AuthorEntity> findOne(Long id);

    boolean doesExists(Long id);

    AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity);
}
