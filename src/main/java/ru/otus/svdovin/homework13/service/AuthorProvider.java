package ru.otus.svdovin.homework13.service;

import ru.otus.svdovin.homework13.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorProvider {
    String createAuthor(Author author);
    Optional<Author> getAuthorById(String id);
    List<Author> getAuthorByName(String name);
    List<Author> getAuthorAll();
    Author updateAuthor(Author author);
    void deleteAuthorById(String id);
    boolean existsById(String id);
    boolean existsByName(String name);
}
