package ru.otus.svdovin.homework13.service;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.svdovin.homework13.domain.Book;
import ru.otus.svdovin.homework13.repository.BookRepository;

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

    private static final String NEW_BOOK_ID = "idBook7";
    private static final String NEW_BOOK_NAME = "Поручик Ржевский";
    private static final String DEFAULT_BOOK_ID = "idBook5";
    private static final String TEST_AUTHOR_ID = "idAuthor1";
    private static final String TEST_GENRE_ID = "idGenre1";
    private static final String TEST_BOOK_ID = "idBook1";
    private static final String TEST_BOOK_NAME = "Book 1";
    private static final String TEST_BOOK_ID_NOT_EXISTS = "idBook6";
    private static final String TEST_BOOK_NAME_NOT_EXISTS = "Book 6";

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private BookProvider bookProvider;

    @Test
    @DisplayName("должен корректно добавлять книгу")
    void shouldCeateBook() {
        val book = Book.builder()
                .bookId(NEW_BOOK_ID)
                .bookName(NEW_BOOK_NAME)
                .build();
        Mockito.when(bookRepository.save(book)).thenReturn(book);
        String newId = bookProvider.createBook(book);
        assertThat(newId).isEqualTo(NEW_BOOK_ID);
    }

    @Test
    @DisplayName("возвращать ожидаемую книгу по ее id")
    void shouldReturnExpectedBookById() {
        val book = Book.builder()
                .bookId(NEW_BOOK_ID)
                .bookName(NEW_BOOK_NAME)
                .build();
        Mockito.when(bookRepository.findById(NEW_BOOK_ID)).thenReturn(Optional.of(book));
        Book actual = bookProvider.getBookById(NEW_BOOK_ID).orElse(null);
        assertThat(actual).isEqualToComparingFieldByField(book);
    }

    @Test
    @DisplayName("возвращать ожидаемую книгу по ее имени")
    void shouldReturnExpectedBookByName() {
        val book = Book.builder()
                .bookId(NEW_BOOK_ID)
                .bookName(NEW_BOOK_NAME)
                .build();
        List<Book> list = new ArrayList<>();
        list.add(book);
        Mockito.when(bookRepository.findByBookName(NEW_BOOK_NAME)).thenReturn(list);
        List<Book> listBooks = bookProvider.getBookByName(NEW_BOOK_NAME);
        assertAll("Книга",
                () -> assertThat(listBooks.size()).isEqualTo(1),
                () -> assertThat(listBooks.get(0)).isEqualToComparingFieldByField(book)
        );
    }

    @Test
    @DisplayName("возвращать все книги")
    void shouldReturnAllBooks() {
        val book = Book.builder()
                .bookId(NEW_BOOK_ID)
                .bookName(NEW_BOOK_NAME)
                .build();
        List<Book> list = new ArrayList<>();
        list.add(book);
        Mockito.when(bookRepository.findAll()).thenReturn(list);
        List<Book> listBooks = bookProvider.getBookAll();
        assertAll("Книга",
                () -> assertThat(listBooks.size()).isEqualTo(1),
                () -> assertThat(listBooks.get(0)).isEqualToComparingFieldByField(book)
        );
    }

    @Test
    @DisplayName("изменять ожидаемую книгу")
    void shouldUpdateExpectedBook() {
        val book = Book.builder()
                .bookId(NEW_BOOK_ID)
                .bookName(NEW_BOOK_NAME)
                .build();
        bookProvider.updateBook(book);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    @DisplayName("удалять ожидаемую книгу")
    void shouldDeleteExpectedBookById() {
        bookProvider.deleteBookById(DEFAULT_BOOK_ID);
        verify(bookRepository, times(1)).deleteById(DEFAULT_BOOK_ID);
    }

    @Test
    @DisplayName("возвращать ожидаемые книги по идентификатору автора")
    void shouldReturnExpectedBookByAuthorId() {
        val book = Book.builder()
                .bookId(NEW_BOOK_ID)
                .bookName(NEW_BOOK_NAME)
                .build();
        List<Book> list = new ArrayList<>();
        list.add(book);
        Mockito.when(bookRepository.findByAuthorsAuthorId(TEST_AUTHOR_ID)).thenReturn(list);
        List<Book> listBooks = bookProvider.getBookByAuthorId(TEST_AUTHOR_ID);
        assertAll("Книга",
                () -> assertThat(listBooks.size()).isEqualTo(1),
                () -> assertThat(listBooks.get(0)).isEqualToComparingFieldByField(book)
        );
    }

    @Test
    @DisplayName("возвращать ожидаемые книги по идентификатору жанра")
    void shouldReturnExpectedBookByGenreId() {
        val book = Book.builder()
                .bookId(NEW_BOOK_ID)
                .bookName(NEW_BOOK_NAME)
                .build();
        List<Book> list = new ArrayList<>();
        list.add(book);
        Mockito.when(bookRepository.findByGenreGenreId(TEST_GENRE_ID)).thenReturn(list);
        List<Book> listBooks = bookProvider.getBookByGenreId(TEST_GENRE_ID);
        assertAll("Книга",
                () -> assertThat(listBooks.size()).isEqualTo(1),
                () -> assertThat(listBooks.get(0)).isEqualToComparingFieldByField(book)
        );
    }

    @Test
    @DisplayName("проверять наличие книги по идентификатору")
    void shouldCheckExistById() {
        Mockito.when(bookRepository.existsById(TEST_BOOK_ID)).thenReturn(true);
        Mockito.when(bookRepository.existsById(TEST_BOOK_ID_NOT_EXISTS)).thenReturn(false);
        assertAll("Существование id книги",
                () -> assertThat(bookProvider.existsById(TEST_BOOK_ID)).isEqualTo(true),
                () -> assertThat(bookProvider.existsById(TEST_BOOK_ID_NOT_EXISTS)).isEqualTo(false)
        );
    }

    @Test
    @DisplayName("проверять наличие книги по наименованию")
    void shouldCheckExistByName() {
        Mockito.when(bookRepository.existsByBookName(TEST_BOOK_NAME)).thenReturn(true);
        Mockito.when(bookRepository.existsByBookName(TEST_BOOK_NAME_NOT_EXISTS)).thenReturn(false);
        assertAll("Существование наименования книги",
                () -> assertThat(bookProvider.existsByName(TEST_BOOK_NAME)).isEqualTo(true),
                () -> assertThat(bookProvider.existsByName(TEST_BOOK_NAME_NOT_EXISTS)).isEqualTo(false)
        );
    }
}