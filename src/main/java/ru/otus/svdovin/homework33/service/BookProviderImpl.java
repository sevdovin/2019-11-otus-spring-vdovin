package ru.otus.svdovin.homework33.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import ru.otus.svdovin.homework33.domain.Author;
import ru.otus.svdovin.homework33.domain.Book;
import ru.otus.svdovin.homework33.domain.Genre;
import ru.otus.svdovin.homework33.dto.BookDto;
import ru.otus.svdovin.homework33.repository.BookRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookProviderImpl implements BookProvider {

    private final BookRepository bookRepository;

    public BookProviderImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @HystrixCommand(fallbackMethod = "defaultCreateBook", commandKey = "books")
    @Override
    public long createBook(Book book) {
        return bookRepository.save(book).getBookId();
    }

    @HystrixCommand(fallbackMethod = "defaultUpdateBook", commandKey = "books")
    @Override
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    @HystrixCommand(fallbackMethod = "defaultDeleteBookById", commandKey = "books")
    @Override
    public void deleteBookById(long id) {
        bookRepository.deleteById(id);
    }

    @HystrixCommand(fallbackMethod = "getDefaultBookById", commandKey = "books")
    @Override
    public Optional<Book> getBookById(long id) {
        return bookRepository.findById(id);
    }

    @HystrixCommand(fallbackMethod = "getDefaultBookByName", commandKey = "books")
    @Override
    public List<Book> getBookByName(String name) {
        return bookRepository.findByBookName(name);
    }

    @HystrixCommand(fallbackMethod = "getDefaultBookAll", commandKey = "books")
    @Override
    public List<BookDto> getBookAll() {
        return bookRepository.findAll().stream().map(b -> BookDto.buildDTO(b)).collect(Collectors.toList());
    }

    @HystrixCommand(fallbackMethod = "getDefaultBookByAuthorId", commandKey = "books")
    @Override
    public List<Book> getBookByAuthorId(long authorId) {
        return bookRepository.findByAuthorsAuthorId(authorId);
    }

    @HystrixCommand(fallbackMethod = "getDefaultBookByGenreId", commandKey = "books")
    @Override
    public List<Book> getBookByGenreId(long genreId) {
        return bookRepository.findByGenreGenreId(genreId);
    }

    @HystrixCommand(fallbackMethod = "defaultExistsById", commandKey = "books")
    @Override
    public boolean existsById(long id) {
        return bookRepository.existsById(id);
    }

    @HystrixCommand(fallbackMethod = "defaultExistsByName", commandKey = "books")
    @Override
    public boolean existsByName(String name) {
        return bookRepository.existsByBookName(name);
    }

    public long defaultCreateBook(Book book) {
        return -1;
    }

    public void defaultDeleteBookById() {
    }

    public Book defaultUpdateBook(Book book) {
        return book;
    }

    public Optional<Book> getDefaultBookById(long id) {
        return Optional.of(Book.builder()
                .bookId((long) -1)
                .bookName("No name book")
                .genre(Genre.builder().genreId((long) -1).genreName("No name genre").build())
                .authors(Collections.singletonList(Author.builder().authorId((long) -1).authorName("No name author").build()))
                .build());
    }

    public List<Book> getDefaultBookByName(String name) {
        return Collections.singletonList(Book.builder()
                .bookId((long) -1)
                .bookName("No name book")
                .genre(Genre.builder().genreId((long) -1).genreName("No name genre").build())
                .authors(Collections.singletonList(Author.builder().authorId((long) -1).authorName("No name author").build()))
                .build());
    }

    public List<BookDto> getDefaultBookAll() {
        return Collections.singletonList(BookDto.builder()
                .bookId((long) -1)
                .bookName("No name book")
                .genre(Genre.builder().genreId((long) -1).genreName("No name genre").build())
                .authorsInfo("No name author")
                .build());
    }

    public List<Book> getDefaultBookByAuthorId(long authorId) {
        return getDefaultBookByName(null);
    }

    public List<Book> getDefaultBookByGenreId(long genreId) {
        return getDefaultBookByName(null);
    }

    public boolean defaultExistsById(long id) {
        return false;
    }

    public boolean defaultExistsByName(String name) {
        return false;
    }
}
