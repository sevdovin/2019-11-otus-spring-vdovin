package ru.otus.svdovin.homework13.service;

import org.springframework.stereotype.Service;
import ru.otus.svdovin.homework13.domain.Author;
import ru.otus.svdovin.homework13.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorProviderImpl implements AuthorProvider {

    private final AuthorRepository authorRepository;

    public AuthorProviderImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public String createAuthor(Author author) {
        return authorRepository.save(author).getAuthorId();
    }

    @Override
    public Optional<Author> getAuthorById(String id) {
        return authorRepository.findById(id);
    }

    @Override
    public List<Author> getAuthorByName(String name) {
        return authorRepository.findByAuthorName(name);
    }

    @Override
    public List<Author> getAuthorAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author updateAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void deleteAuthorById(String id) {
        authorRepository.deleteById(id);
    }

    @Override
    public boolean existsById(String id) {
        return authorRepository.existsById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return authorRepository.existsByAuthorName(name);
    }
}
