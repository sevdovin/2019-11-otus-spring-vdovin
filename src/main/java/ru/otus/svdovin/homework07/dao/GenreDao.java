package ru.otus.svdovin.homework07.dao;

import ru.otus.svdovin.homework07.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    long insert(Genre genre);
    void update(Genre genre);
    void deleteById(long id);
    Optional<Genre> getById(long id);
    List<Genre> getByName(String name);
    List<Genre> getAll();
    int count();
}
