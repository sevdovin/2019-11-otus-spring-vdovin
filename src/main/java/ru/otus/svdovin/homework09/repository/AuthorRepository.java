package ru.otus.svdovin.homework09.repository;

import ru.otus.svdovin.homework09.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    long insert(Author author);
    Author update(Author author);
    void deleteById(long id);
    Optional<Author> getById(long id);
    List<Author> getByName(String name);
    List<Author> getAll();
    long count();
    boolean existsById(long id);
    boolean existsByName(String name);
}
