package ru.otus.svdovin.homework13.service;

import ru.otus.svdovin.homework13.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookProvider {
    String createBook(Book book);
    Book updateBook(Book book);
    void deleteBookById(String id);
    Optional<Book> getBookById(String id);
    List<Book> getBookByName(String name);
    List<Book> getBookAll();
    List<Book> getBookByAuthorId(String authorId);
    List<Book> getBookByGenreId(String genreId);
    boolean existsById(String id);
    boolean existsByName(String name);
}
