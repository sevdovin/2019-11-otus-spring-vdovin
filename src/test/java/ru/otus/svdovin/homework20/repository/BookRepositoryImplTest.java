package ru.otus.svdovin.homework20.repository;

import com.github.cloudyrock.mongock.Mongock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import ru.otus.svdovin.homework20.domain.Author;
import ru.otus.svdovin.homework20.domain.Book;
import ru.otus.svdovin.homework20.domain.Genre;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Репозиторий на основе Mongo для работы с книгами должен ")
@DataMongoTest
@ComponentScan({"ru.otus.svdovin.homework20.testdata"})
class BookRepositoryImplTest {

    private static final int BOOKS_COUNT_INT = 5;

    private Author author1 = Author.builder().authorId("idAuthor1").authorName("Author 1").build();
    private Author author2 = Author.builder().authorId("idAuthor2").authorName("Author 2").build();
    private Author author3 = Author.builder().authorId("idAuthor3").authorName("Author 3").build();

    private Genre genre1 = Genre.builder().genreId("idGenre1").genreName("Genre 1").build();
    private Genre genre2 = Genre.builder().genreId("idGenre2").genreName("Genre 2").build();
    private Genre genre3 = Genre.builder().genreId("idGenre3").genreName("Genre 3").build();

    private Book book1 = Book.builder().bookId("idBook1").bookName("Book 1").genre(genre1).authors(Arrays.asList(author1)).build();
    private Book book2 = Book.builder().bookId("idBook2").bookName("Book 2").genre(genre1).authors(Arrays.asList(author2)).build();
    private Book book3 = Book.builder().bookId("idBook3").bookName("Book 3").genre(genre2).authors(Arrays.asList(author2)).build();
    private Book book4 = Book.builder().bookId("idBook4").bookName("Book 4").genre(genre1).authors(Arrays.asList(author3)).build();
    private Book book5 = Book.builder().bookId("idBook5").bookName("Book 5").genre(genre3).authors(Arrays.asList(author3)).build();

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private Mongock mongock;

    @BeforeEach
    public void init() {
        mongock.execute();
    }

    @DisplayName("возвращать список всех книг")
    @Test
    void shouldFindBookAll() {
        Flux<Book> books = bookRepository.findAll();
        StepVerifier.create(books)
                .recordWith(ArrayList::new)
                .expectNextCount(BOOKS_COUNT_INT)
                .consumeRecordedWith(results -> {
                    assertThat(results).hasSize(BOOKS_COUNT_INT);
                    assertThat(results).contains(book1, book2, book3, book4, book5);
                }).expectComplete().verify();
    }

    @DisplayName("возвращать книгу по идентификатору")
    @Test
    void shouldGetBookById() {
        StepVerifier.create(bookRepository.findById(book1.getBookId()))
                .assertNext(book -> assertThat(book1).isEqualTo(book1))
                .expectComplete()
                .verify();
    }

    @DisplayName("добавлять новую книгу")
    @Test
    void shouldCreateBook() {
        Book newbook = Book.builder().bookName("Book 6").genre(genre1).authors(Arrays.asList(author1)).build();
        StepVerifier.create(bookRepository.save(newbook))
                .assertNext(book -> assertNotNull(book.getBookId()))
                .expectComplete()
                .verify();
    }

    @DisplayName("изменять существующую книгу")
    @Test
    void shouldUpdateBook() {
        Book bookForModify = Book.builder()
                .bookId(book1.getBookId())
                .authors(Arrays.asList(author1))
                .genre(genre1)
                .bookName("NEW NAME")
                .build();
        StepVerifier.create(bookRepository.save(bookForModify))
                .assertNext(book -> assertThat(book).isEqualTo(bookForModify))
                .expectComplete()
                .verify();
    }

    @DisplayName("удалять книгу")
    @Test
    void shouldDeleteBook() {
        bookRepository.deleteById(book2.getBookId()).block();
        StepVerifier.create(bookRepository.findById(book2.getBookId()))
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }
}