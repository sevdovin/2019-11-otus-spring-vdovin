package ru.otus.svdovin.homework33.service;

import org.springframework.stereotype.Service;
import ru.otus.svdovin.homework33.domain.Genre;
import ru.otus.svdovin.homework33.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GenreProviderImpl implements GenreProvider {

    private final GenreRepository genreRepository;

    public GenreProviderImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public long createGenre(Genre genre) {
        return genreRepository.save(genre).getGenreId();
    }

    @Override
    public Optional<Genre> getGenreById(long id) {
        return genreRepository.findById(id);
    }

    @Override
    public List<Genre> getGenreByName(String name) {
        return genreRepository.findByGenreName(name);
    }

    @Override
    public List<Genre> getGenreAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre updateGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public void deleteGenreById(long id) {
        genreRepository.deleteById(id);
    }

    @Override
    public boolean existsById(long id) {
        return genreRepository.existsById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return genreRepository.existsByGenreName(name);
    }
}
