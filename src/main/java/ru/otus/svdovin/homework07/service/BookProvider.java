package ru.otus.svdovin.homework07.service;

import ru.otus.svdovin.homework07.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookProvider {
    long createBook(Book book);
    void updateBook(Book book);
    void deleteBookById(long id);
    Optional<Book> getBookById(long id);
    List<Book> getBookByName(String name);
    List<Book> getBookAll();
    List<Book> getBookByAuthorId(long authorId);
    List<Book> getBookByGenreId(long genreId);
}
