package ru.otus.svdovin.homework09.repository;

import ru.otus.svdovin.homework09.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    long insert(Genre genre);
    Genre update(Genre genre);
    void deleteById(long id);
    Optional<Genre> getById(long id);
    List<Genre> getByName(String name);
    List<Genre> getAll();
    long count();
    boolean existsById(long id);
    boolean existsByName(String name);
}
