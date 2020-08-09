package ru.otus.svdovin.homework24.service;

import ru.otus.svdovin.homework24.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorProvider {
    long createAuthor(Author author);
    Optional<Author> getAuthorById(long id);
    List<Author> getAuthorByName(String name);
    List<Author> getAuthorAll();
    Author updateAuthor(Author author);
    void deleteAuthorById(long id);
    boolean existsById(long id);
    boolean existsByName(String name);
}
