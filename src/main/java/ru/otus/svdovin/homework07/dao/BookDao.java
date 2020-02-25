package ru.otus.svdovin.homework07.dao;

import ru.otus.svdovin.homework07.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    long insert(Book book);
    void update(Book book);
    void deleteById(long id);
    Optional<Book> getById(long id);
    List<Book> getByName(String name);
    List<Book> getByAuthorId(long authorId);
    List<Book> getByGenreId(long genreId);
    List<Book> getAll();
    int count();
}
