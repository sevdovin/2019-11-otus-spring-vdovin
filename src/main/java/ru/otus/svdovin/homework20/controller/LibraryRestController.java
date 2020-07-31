package ru.otus.svdovin.homework20.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.svdovin.homework20.domain.Author;
import ru.otus.svdovin.homework20.domain.Book;
import ru.otus.svdovin.homework20.domain.Genre;
import ru.otus.svdovin.homework20.dto.BookDto;
import ru.otus.svdovin.homework20.repository.AuthorRepository;
import ru.otus.svdovin.homework20.repository.BookRepository;
import ru.otus.svdovin.homework20.repository.GenreRepository;

@RequiredArgsConstructor
@RestController
public class LibraryRestController {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @GetMapping("/api/v1/author")
    public Flux<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    @GetMapping("/api/v1/genre")
    public Flux<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

    @GetMapping("/api/v1/book")
    public Flux<BookDto> findAllBooks() {
        return bookRepository.findAll().map(BookDto::buildDTO);
    }

    @GetMapping("/api/v1/book/{id}")
    public Mono<Book> getBookById(@PathVariable("id") String bookId) {
        return bookRepository.findById(bookId);
    }

    @PostMapping("/api/v1/book")
    public Mono<Book> createBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PutMapping("/api/v1/book")
    public Mono<Book> updateBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping("/api/v1/book/{id}")
    public Mono<Void> deleteBookById(@PathVariable("id") String bookId) {
        return bookRepository.deleteById(bookId);
    }
}