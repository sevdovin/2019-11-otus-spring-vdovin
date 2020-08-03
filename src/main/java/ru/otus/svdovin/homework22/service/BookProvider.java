package ru.otus.svdovin.homework22.service;

import ru.otus.svdovin.homework22.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookProvider {
    long createBook(Book book);
    Book updateBook(Book book);
    void deleteBookById(long id);
    Optional<Book> getBookById(long id);
    List<Book> getBookByName(String name);
    List<Book> getBookAll();
    List<Book> getBookByAuthorId(long authorId);
    List<Book> getBookByGenreId(long genreId);
    boolean existsById(long id);
    boolean existsByName(String name);
}
