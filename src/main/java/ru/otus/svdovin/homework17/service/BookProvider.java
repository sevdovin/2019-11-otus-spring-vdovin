package ru.otus.svdovin.homework17.service;

import ru.otus.svdovin.homework17.domain.Book;
import ru.otus.svdovin.homework17.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookProvider {
    long createBook(Book book);
    Book updateBook(Book book);
    void deleteBookById(long id);
    Optional<Book> getBookById(long id);
    List<Book> getBookByName(String name);
    List<BookDto> getBookAll();
    List<Book> getBookByAuthorId(long authorId);
    List<Book> getBookByGenreId(long genreId);
    boolean existsById(long id);
    boolean existsByName(String name);
}
