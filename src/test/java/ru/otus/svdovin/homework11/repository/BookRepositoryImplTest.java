package ru.otus.svdovin.homework11.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.svdovin.homework11.domain.Author;
import ru.otus.svdovin.homework11.domain.Book;
import ru.otus.svdovin.homework11.domain.Genre;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Репозиторий на основе Jpa для работы с авторами должен ")
@DataJpaTest
class BookRepositoryImplTest {

    private static final String NEW_BOOK_NAME = "Вася";
    private static final long TEST_BOOK_ID = 1;
    private static final String TEST_BOOK_NAME = "Book 1";
    private static final long TEST_BOOK_ID_NOT_EXISTS = 6;
    private static final String TEST_BOOK_NAME_NOT_EXISTS = "Book 6";
    private static final long TEST_AUTHOR_ID = 1;
    private static final String TEST_AUTHOR_NAME = "Author 1";
    private static final int TEST_AUTHOR_ID_BOOK_COUNT = 1;
    private static final long TEST_GENRE_ID = 1;
    private static final String TEST_GENRE_NAME = "Genre 1";
    private static final int TEST_GENRE_ID_BOOK_COUNT = 3;
    private static final int BOOKS_COUNT_INT = 5;
    private static final long BOOKS_COUNT_LONG = 5;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        List<Author> list = Collections.singletonList(new Author(TEST_AUTHOR_ID, TEST_AUTHOR_NAME));
        val genre = new Genre(TEST_GENRE_ID, TEST_GENRE_NAME);
        Book book = new Book(0, NEW_BOOK_NAME, genre, list);
        long newId = bookRepository.save(book).getBookId();
        book.setBookId(newId);
        val actual = em.find(Book.class, newId);
        assertThat(actual).isNotNull().isEqualToComparingFieldByField(book);
    }

    @DisplayName("изменять наименование книги в БД")
    @Test
    void shouldUpdateBook() {
        val book1 = em.find(Book.class, TEST_BOOK_ID);
        String oldName = book1.getBookName();
        em.detach(book1);
        book1.setBookName(NEW_BOOK_NAME);
        val book2 = bookRepository.save(book1);
        assertThat(book2.getBookName()).isNotEqualTo(oldName).isEqualTo(NEW_BOOK_NAME);
    }

    @DisplayName("удалять книгу")
    @Test
    void shouldDeleteBook() {
        val book1 = em.find(Book.class, TEST_BOOK_ID);
        assertThat(book1).isNotNull();
        em.detach(book1);
        bookRepository.deleteById(TEST_BOOK_ID);
        val book2 = em.find(Book.class, TEST_BOOK_ID);
        assertThat(book2).isNull();
    }

    @DisplayName("возвращать книгу по ее id")
    @Test
    void shouldFindBookById() {
        Optional<Book> book = bookRepository.findById(TEST_BOOK_ID);
        assertThat(book).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("bookName", TEST_BOOK_NAME);
    }

    @DisplayName("возвращать список книг по наименованию")
    @Test
    void shouldFindBooksByName() {
        assertAll("Список книг по наименованию",
                () -> assertThat(bookRepository.findByBookName(TEST_BOOK_NAME)).isNotNull().hasSize(1),
                () -> assertThat(bookRepository.findByBookName(TEST_BOOK_NAME_NOT_EXISTS)).isNotNull().hasSize(0)
        );
    }

    @DisplayName("возвращать список книг по id автора")
    @Test
    void testGetBooksByAuthorId() {
        List<Book> bookList = bookRepository.findByAuthorsAuthorId(TEST_BOOK_ID);
        assertThat(bookList).isNotNull().hasSize(TEST_AUTHOR_ID_BOOK_COUNT);
    }

    @DisplayName("возвращать список книг по id жанра")
    @Test
    void testGetBooksByGenreId() {
        List<Book> bookList = bookRepository.findByGenreGenreId(TEST_GENRE_ID);
        assertThat(bookList).isNotNull().hasSize(TEST_GENRE_ID_BOOK_COUNT);
    }

    @DisplayName("возвращать список всех книг")
    @Test
    void shouldFindBookAll() {
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).isNotNull().hasSize(BOOKS_COUNT_INT);
    }

    @DisplayName("возвращать количество книг")
    @Test
    void shouldReturnBookCount() {
        long booksCount = bookRepository.count();
        assertThat(booksCount).isEqualTo(BOOKS_COUNT_LONG);
    }

    @DisplayName("проверять наличие книги по идентификатору")
    @Test
    void shouldCheckExistById() {
        assertAll("Существование id книги",
                () -> assertThat(bookRepository.existsById(TEST_BOOK_ID)).isEqualTo(true),
                () -> assertThat(bookRepository.existsById(TEST_BOOK_ID_NOT_EXISTS)).isEqualTo(false)
        );
    }

    @DisplayName("проверять наличие книги по наименованию")
    @Test
    void shouldCheckExistByName() {
        assertAll("Существование наименования книги",
                () -> assertThat(bookRepository.existsByBookName(TEST_BOOK_NAME)).isEqualTo(true),
                () -> assertThat(bookRepository.existsByBookName(TEST_BOOK_NAME_NOT_EXISTS)).isEqualTo(false)
        );
    }
}