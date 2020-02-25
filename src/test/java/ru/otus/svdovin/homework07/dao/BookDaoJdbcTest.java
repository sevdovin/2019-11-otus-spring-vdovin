package ru.otus.svdovin.homework07.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.svdovin.homework07.domain.Author;
import ru.otus.svdovin.homework07.domain.Book;
import ru.otus.svdovin.homework07.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Dao для работы с книгами должно")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    private static final long NEW_BOOK_ID = 7;
    private static final String NEW_BOOK_NAME = "Вася";
    private static final long TEST_AUTHOR_ID = 1;
    private static final String TEST_AUTHOR_NAME = "Author 1";
    private static final long TEST_GENRE_ID = 1;
    private static final String TEST_GENRE_NAME = "Genre 1";
    private static final long DEFAULT_BOOK_ID = 4;
    private static final String DEFAULT_BOOK_NAME = "Book 4";
    private static final int EXPECTED_BOOKS_COUNT = 5;

    @Autowired
    private BookDaoJdbc jdbc;

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        Author author = new Author(TEST_AUTHOR_ID, TEST_AUTHOR_NAME);
        Genre genre = new Genre(TEST_GENRE_ID, TEST_GENRE_NAME);
        Book book = new Book(0, NEW_BOOK_NAME, author, genre);
        long newId = jdbc.insert(book);
        book.setBookId(newId);
        Book actual = jdbc.getById(newId).orElse(null);
        assertThat(actual).isEqualToComparingFieldByField(book);
    }

    @DisplayName("возвращать ожидаемую книгу по ее id")
    @Test
    void shouldReturnExpectedBookById() {
        Book actual = jdbc.getById(DEFAULT_BOOK_ID).orElse(null);
        assertThat(actual.getBookName()).isEqualTo(DEFAULT_BOOK_NAME);
    }

    @DisplayName("возвращать ожидаемое количество книг в БД")
    @Test
    void shouldReturnExpectedBookCount() {
        assertThat(jdbc.count()).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("удалять ожидаемую кгигу по ее id")
    @Test
    void shouldDeleteExpectedBookById() {
        jdbc.deleteById(DEFAULT_BOOK_ID);
        Book actual = jdbc.getById(DEFAULT_BOOK_ID).orElse(null);
        assertThat(actual).isEqualTo(null);
    }

    @DisplayName("возвращать ожидаемую книгу по ее имени")
    @Test
    void shouldReturnExpectedBookByName() {
        List<Book> listBooks = jdbc.getByName(DEFAULT_BOOK_NAME);
        assertAll("Книга",
                () -> assertThat(listBooks.size()).isEqualTo(1),
                () -> assertThat(listBooks.get(0).getBookName()).isEqualTo(DEFAULT_BOOK_NAME)
        );
    }

    @DisplayName("возвращать все книги")
    @Test
    void shouldReturnAllBooks() {
        List<Book> listBooks = jdbc.getAll();
        assertThat(listBooks.size()).isEqualTo(5);
    }

    @DisplayName("возвращать ожидаемые книги по идентификатору автора")
    @Test
    void shouldReturnExpectedBookByAuthorId() {
        List<Book> listBooks = jdbc.getByAuthorId(TEST_AUTHOR_ID);
        assertThat(listBooks.size()).isEqualTo(1);
    }

    @DisplayName("возвращать ожидаемые книги по идентификатору жанра")
    @Test
    void shouldReturnExpectedBookByGenreId() {
        List<Book> listBooks = jdbc.getByGenreId(TEST_GENRE_ID);
        assertThat(listBooks.size()).isEqualTo(3);
    }
}