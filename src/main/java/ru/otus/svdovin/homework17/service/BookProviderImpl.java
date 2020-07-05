package ru.otus.svdovin.homework17.service;

import org.springframework.stereotype.Service;
import ru.otus.svdovin.homework17.domain.Book;
import ru.otus.svdovin.homework17.dto.BookDto;
import ru.otus.svdovin.homework17.repository.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookProviderImpl implements BookProvider {

    private final BookRepository bookRepository;

    public BookProviderImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public long createBook(Book book) {
        return bookRepository.save(book).getBookId();
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBookById(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Optional<Book> getBookById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> getBookByName(String name) {
        return bookRepository.findByBookName(name);
    }

    @Override
    public List<BookDto> getBookAll() {
        return bookRepository.findAll().stream().map(b -> BookDto.buildDTO(b)).collect(Collectors.toList());
    }

    @Override
    public List<Book> getBookByAuthorId(long authorId) {
        return bookRepository.findByAuthorsAuthorId(authorId);
    }

    @Override
    public List<Book> getBookByGenreId(long genreId) {
        return bookRepository.findByGenreGenreId(genreId);
    }

    @Override
    public boolean existsById(long id) {
        return bookRepository.existsById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return bookRepository.existsByBookName(name);
    }
}
