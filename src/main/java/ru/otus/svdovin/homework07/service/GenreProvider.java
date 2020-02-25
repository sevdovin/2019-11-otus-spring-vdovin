package ru.otus.svdovin.homework07.service;

import ru.otus.svdovin.homework07.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreProvider {
    long createGenre(Genre genre);
    Optional<Genre> getGenreById(long id);
    List<Genre> getGenreByName(String name);
    List<Genre> getGenreAll();
    void updateGenre(Genre genre);
    void deleteGenreById(long id);
}
