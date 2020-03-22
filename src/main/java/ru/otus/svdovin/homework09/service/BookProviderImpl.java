package ru.otus.svdovin.homework09.service;

import org.springframework.stereotype.Service;
import ru.otus.svdovin.homework09.domain.Book;
import ru.otus.svdovin.homework09.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookProviderImpl implements BookProvider {

    private final BookRepository bookRepository;

    public BookProviderImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public long createBook(Book book) {
        return bookRepository.insert(book);
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.update(book);
    }

    @Override
    public void deleteBookById(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Optional<Book> getBookById(long id) {
        return bookRepository.getById(id);
    }

    @Override
    public List<Book> getBookByName(String name) {
        return bookRepository.getByName(name);
    }

    @Override
    public List<Book> getBookAll() {
        return bookRepository.getAll();
    }

    @Override
    public List<Book> getBookByAuthorId(long authorId) {
        return bookRepository.getByAuthorId(authorId);
    }

    @Override
    public List<Book> getBookByGenreId(long genreId) {
        return bookRepository.getByGenreId(genreId);
    }

    @Override
    public boolean existsById(long id) {
        return bookRepository.existsById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return bookRepository.existsByName(name);
    }
}
