package com.harshharia.database.services;

import com.harshharia.database.domain.entities.AuthorEntity;

import java.util.List;

public interface AuthorService {
    AuthorEntity createAuthor(AuthorEntity authorEntity);

    List<AuthorEntity> findAll();
}
