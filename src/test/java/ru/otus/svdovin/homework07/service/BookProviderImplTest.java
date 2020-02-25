package ru.otus.svdovin.homework07.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.svdovin.homework07.dao.BookDao;
import ru.otus.svdovin.homework07.domain.Author;
import ru.otus.svdovin.homework07.domain.Book;
import ru.otus.svdovin.homework07.domain.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Сервис для работы с книгами ")
@SpringBootTest(classes = {BookProviderImpl.class})
class BookProviderImplTest {

    private static final long NEW_BOOK_ID = 7;
    private static final String NEW_BOOK_NAME = "Поручик Ржевский";
    private static final long DEFAULT_BOOK_ID = 5;
    private static final long TEST_AUTHOR_ID = 1;
    private static final String TEST_AUTHOR_NAME = "Author 1";
    private static final long TEST_GENRE_ID = 1;
    private static final String TEST_GENRE_NAME = "Genre 1";


    @MockBean
    private BookDao bookDao;

    @MockBean
    private MessageService messageService;

    @Autowired
    private BookProvider bookProvider;

    @Test
    @DisplayName("должен корректно добавлять книгу")
    void shouldCeateBook() {
        Book book = new Book(NEW_BOOK_ID, NEW_BOOK_NAME, new Author(TEST_AUTHOR_ID, TEST_AUTHOR_NAME), new Genre(TEST_GENRE_ID, TEST_GENRE_NAME));
        Mockito.when(bookDao.insert(book)).thenReturn(NEW_BOOK_ID);
        long newId = bookProvider.createBook(book);
        assertThat(newId).isEqualTo(NEW_BOOK_ID);
    }

    @Test
    @DisplayName("возвращать ожидаемую книгу по ее id")
    void shouldReturnExpectedBookById() {
        Book book = new Book(NEW_BOOK_ID, NEW_BOOK_NAME, new Author(TEST_AUTHOR_ID, TEST_AUTHOR_NAME), new Genre(TEST_GENRE_ID, TEST_GENRE_NAME));
        Mockito.when(bookDao.getById(NEW_BOOK_ID)).thenReturn(Optional.of(book));
        Book actual = bookProvider.getBookById(NEW_BOOK_ID).orElse(null);
        assertThat(actual).isEqualToComparingFieldByField(book);
    }

    @Test
    @DisplayName("возвращать ожидаемую книгу по ее имени")
    void shouldReturnExpectedBookByName() {
        Book book = new Book(NEW_BOOK_ID, NEW_BOOK_NAME, new Author(TEST_AUTHOR_ID, TEST_AUTHOR_NAME), new Genre(TEST_GENRE_ID, TEST_GENRE_NAME));
        List<Book> list = new ArrayList<>();
        list.add(book);
        Mockito.when(bookDao.getByName(NEW_BOOK_NAME)).thenReturn(list);
        List<Book> listBooks = bookProvider.getBookByName(NEW_BOOK_NAME);
        assertAll("Книга",
                () -> assertThat(listBooks.size()).isEqualTo(1),
                () -> assertThat(listBooks.get(0)).isEqualToComparingFieldByField(book)
        );
    }

    @Test
    @DisplayName("возвращать все книги")
    void shouldReturnAllBooks() {
        Book book = new Book(NEW_BOOK_ID, NEW_BOOK_NAME, new Author(TEST_AUTHOR_ID, TEST_AUTHOR_NAME), new Genre(TEST_GENRE_ID, TEST_GENRE_NAME));
        List<Book> list = new ArrayList<>();
        list.add(book);
        Mockito.when(bookDao.getAll()).thenReturn(list);
        List<Book> listBooks = bookProvider.getBookAll();
        assertAll("Книга",
                () -> assertThat(listBooks.size()).isEqualTo(1),
                () -> assertThat(listBooks.get(0)).isEqualToComparingFieldByField(book)
        );
    }

    @Test
    @DisplayName("изменять ожидаемую книгу")
    void shouldUpdateExpectedBook() {
        Book book = new Book(NEW_BOOK_ID, NEW_BOOK_NAME, new Author(TEST_AUTHOR_ID, TEST_AUTHOR_NAME), new Genre(TEST_GENRE_ID, TEST_GENRE_NAME));
        bookProvider.updateBook(book);
        verify(bookDao, times(1)).update(book);
    }

    @Test
    @DisplayName("удалять ожидаемую книгу")
    void shouldDeleteExpectedBookById() {
        bookProvider.deleteBookById(DEFAULT_BOOK_ID);
        verify(bookDao, times(1)).deleteById(DEFAULT_BOOK_ID);
    }

    @Test
    @DisplayName("возвращать ожидаемые книги по идентификатору автора")
    void shouldReturnExpectedBookByAuthorId() {
        Book book = new Book(NEW_BOOK_ID, NEW_BOOK_NAME, new Author(TEST_AUTHOR_ID, TEST_AUTHOR_NAME), new Genre(TEST_GENRE_ID, TEST_GENRE_NAME));
        List<Book> list = new ArrayList<>();
        list.add(book);
        Mockito.when(bookDao.getByAuthorId(TEST_AUTHOR_ID)).thenReturn(list);
        List<Book> listBooks = bookProvider.getBookByAuthorId(TEST_AUTHOR_ID);
        assertAll("Книга",
                () -> assertThat(listBooks.size()).isEqualTo(1),
                () -> assertThat(listBooks.get(0)).isEqualToComparingFieldByField(book)
        );
    }

    @Test
    @DisplayName("возвращать ожидаемые книги по идентификатору жанра")
    void shouldReturnExpectedBookByGenreId() {
        Book book = new Book(NEW_BOOK_ID, NEW_BOOK_NAME, new Author(TEST_AUTHOR_ID, TEST_AUTHOR_NAME), new Genre(TEST_GENRE_ID, TEST_GENRE_NAME));
        List<Book> list = new ArrayList<>();
        list.add(book);
        Mockito.when(bookDao.getByGenreId(TEST_GENRE_ID)).thenReturn(list);
        List<Book> listBooks = bookProvider.getBookByGenreId(TEST_GENRE_ID);
        assertAll("Книга",
                () -> assertThat(listBooks.size()).isEqualTo(1),
                () -> assertThat(listBooks.get(0)).isEqualToComparingFieldByField(book)
        );
    }
}