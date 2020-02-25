package ru.otus.svdovin.homework07.service;

import ru.otus.svdovin.homework07.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorProvider {
    long createAuthor(Author author);
    Optional<Author> getAuthorById(long id);
    List<Author> getAuthorByName(String name);
    List<Author> getAuthorAll();
    void updateAuthor(Author author);
    void deleteAuthorById(long id);
}
