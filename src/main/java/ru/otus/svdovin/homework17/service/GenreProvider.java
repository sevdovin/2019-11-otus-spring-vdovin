package ru.otus.svdovin.homework17.service;

import ru.otus.svdovin.homework17.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreProvider {
    long createGenre(Genre genre);
    Optional<Genre> getGenreById(long id);
    List<Genre> getGenreByName(String name);
    List<Genre> getGenreAll();
    Genre updateGenre(Genre genre);
    void deleteGenreById(long id);
    boolean existsById(long id);
    boolean existsByName(String name);
}
