package ru.otus.svdovin.homework13.service;

import ru.otus.svdovin.homework13.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreProvider {
    String createGenre(Genre genre);
    Optional<Genre> getGenreById(String id);
    List<Genre> getGenreByName(String name);
    List<Genre> getGenreAll();
    Genre updateGenre(Genre genre);
    void deleteGenreById(String id);
    boolean existsById(String id);
    boolean existsByName(String name);
}
