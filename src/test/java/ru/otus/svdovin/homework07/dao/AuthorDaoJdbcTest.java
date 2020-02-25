package ru.otus.svdovin.homework07.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.svdovin.homework07.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Dao для работы с авторами должно")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    private static final long NEW_AUTHOR_ID = 7;
    private static final String NEW_AUTHOR_NAME = "Вася";
    private static final long DEFAULT_AUTHOR_ID = 4;
    private static final String DEFAULT_AUTHOR_NAME = "Author 4";
    private static final String DEFAULT_AUTHOR_NEW_NAME = "Author 4";
    private static final int EXPECTED_AUTHORS_COUNT = 4;

    @Autowired
    private AuthorDaoJdbc jdbc;

    @DisplayName("добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {
        Author author = new Author(NEW_AUTHOR_ID, NEW_AUTHOR_NAME);
        long newId = jdbc.insert(author);
        author.setAuthorId(newId);
        Author actual = jdbc.getById(newId).orElse(null);
        assertThat(actual).isEqualToComparingFieldByField(author);
    }

    @DisplayName("возвращать ожидаемого автора по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        Author actual = jdbc.getById(DEFAULT_AUTHOR_ID).orElse(null);
        assertThat(actual.getAuthorName()).isEqualTo(DEFAULT_AUTHOR_NAME);
    }

    @DisplayName("возвращать ожидаемое количество авторов в БД")
    @Test
    void shouldReturnExpectedAuthorCount() {
        assertThat(jdbc.count()).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

    @DisplayName("удалять ожидаемого автора по его id")
    @Test
    void shouldDeleteExpectedAuthorById() {
        jdbc.deleteById(DEFAULT_AUTHOR_ID);
        Author actual = jdbc.getById(DEFAULT_AUTHOR_ID).orElse(null);
        assertThat(actual).isEqualTo(null);
    }

    @DisplayName("возвращать ожидаемого автора по его имени")
    @Test
    void shouldReturnExpectedAuthorByName() {
        List<Author> listAuthors = jdbc.getByName(DEFAULT_AUTHOR_NAME);
        assertAll("Автор",
                () -> assertThat(listAuthors.size()).isEqualTo(1),
                () -> assertThat(listAuthors.get(0).getAuthorName()).isEqualTo(DEFAULT_AUTHOR_NAME)
        );
    }

    @DisplayName("возвращать всех авторов")
    @Test
    void shouldReturnAllAuthors() {
        List<Author> listAuthors = jdbc.getAll();
        assertThat(listAuthors.size()).isEqualTo(4);
    }

    @DisplayName("изменять ожидаемого автора")
    @Test
    void shouldUpdateExpectedAuthor() {
        Author author = new Author(DEFAULT_AUTHOR_ID, DEFAULT_AUTHOR_NEW_NAME);
        jdbc.update(author);
        Author actual = jdbc.getById(DEFAULT_AUTHOR_ID).orElse(null);
        assertThat(actual).isEqualToComparingFieldByField(author);
    }
}
