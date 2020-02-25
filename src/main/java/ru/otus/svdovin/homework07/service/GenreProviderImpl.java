package ru.otus.svdovin.homework07.service;

import org.springframework.stereotype.Service;
import ru.otus.svdovin.homework07.dao.GenreDao;
import ru.otus.svdovin.homework07.domain.Genre;

import java.util.List;
import java.util.Optional;

@Service
public class GenreProviderImpl implements GenreProvider {

    private final GenreDao genreDao;
    private final MessageService ms;

    public GenreProviderImpl(GenreDao genreDao, MessageService ms) {
        this.genreDao = genreDao;
        this.ms = ms;
    }

    @Override
    public long createGenre(Genre genre) {
        return genreDao.insert(genre);
    }

    @Override
    public Optional<Genre> getGenreById(long id) {
        return genreDao.getById(id);
    }

    @Override
    public List<Genre> getGenreByName(String name) {
        return genreDao.getByName(name);
    }

    @Override
    public List<Genre> getGenreAll() {
        return genreDao.getAll();
    }

    @Override
    public void updateGenre(Genre genre) {
        genreDao.update(genre);
    }

    @Override
    public void deleteGenreById(long id) {
        genreDao.deleteById(id);
    }
}
