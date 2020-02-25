package ru.otus.svdovin.homework07.dao;

import ru.otus.svdovin.homework07.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    long insert(Author author);
    void update(Author author);
    void deleteById(long id);
    Optional<Author> getById(long id);
    List<Author> getByName(String name);
    List<Author> getAll();
    int count();
}
