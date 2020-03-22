package ru.otus.svdovin.homework09.repository;

import ru.otus.svdovin.homework09.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    long insert(Book book);
    Book update(Book book);
    void deleteById(long id);
    Optional<Book> getById(long id);
    List<Book> getByName(String name);
    List<Book> getByAuthorId(long authorId);
    List<Book> getByGenreId(long genreId);
    List<Book> getAll();
    long count();
    boolean existsById(long id);
    boolean existsByName(String name);
}

