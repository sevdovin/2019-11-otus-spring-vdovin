package ru.otus.svdovin.homework13.service;

import org.springframework.stereotype.Service;
import ru.otus.svdovin.homework13.domain.Book;
import ru.otus.svdovin.homework13.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookProviderImpl implements BookProvider {

    private final BookRepository bookRepository;

    public BookProviderImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public String createBook(Book book) {
        return bookRepository.save(book).getBookId();
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBookById(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> getBookByName(String name) {
        return bookRepository.findByBookName(name);
    }

    @Override
    public List<Book> getBookAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBookByAuthorId(String authorId) {
        return bookRepository.findByAuthorsAuthorId(authorId);
    }

    @Override
    public List<Book> getBookByGenreId(String genreId) {
        return bookRepository.findByGenreGenreId(genreId);
    }

    @Override
    public boolean existsById(String id) {
        return bookRepository.existsById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return bookRepository.existsByBookName(name);
    }
}
