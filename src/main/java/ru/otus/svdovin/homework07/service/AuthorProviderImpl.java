package ru.otus.svdovin.homework07.service;

import org.springframework.stereotype.Service;
import ru.otus.svdovin.homework07.dao.AuthorDao;
import ru.otus.svdovin.homework07.domain.Author;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorProviderImpl implements AuthorProvider {

    private final AuthorDao authorDao;
    private final MessageService ms;

    public AuthorProviderImpl(AuthorDao authorDao, MessageService ms) {
        this.authorDao = authorDao;
        this.ms = ms;
    }

    @Override
    public long createAuthor(Author author) {
        return authorDao.insert(author);
    }

    @Override
    public Optional<Author> getAuthorById(long id) {
        return authorDao.getById(id);
    }

    @Override
    public List<Author> getAuthorByName(String name) {
        return authorDao.getByName(name);
    }

    @Override
    public List<Author> getAuthorAll() {
        return authorDao.getAll();
    }

    @Override
    public void updateAuthor(Author author) {
        authorDao.update(author);
    }

    @Override
    public void deleteAuthorById(long id) {
        authorDao.deleteById(id);
    }
}
