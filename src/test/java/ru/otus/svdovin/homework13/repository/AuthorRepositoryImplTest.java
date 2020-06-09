package ru.otus.svdovin.homework13.repository;

import com.github.cloudyrock.mongock.Mongock;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.svdovin.homework13.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Репозиторий на основе Mongo для работы с авторами должен ")
@DataMongoTest
@ComponentScan({"ru.otus.svdovin.homework13.testdata"})
class AuthorRepositoryImplTest {

    private static final String AUTHOR_ID_EXIST = "idAuthor1";
    private static final String AUTHOR_NEW_NAME = "New Author";
    private static final String AUTHOR_NAME_EXISTS = "Author 1";
    private static final String AUTHOR_NAME_NOT_EXISTS = "Author 5";
    private static final int AUTHORS_COUNT_INT = 4;
    private static final long AUTHORS_COUNT_LONG = 4;
    private static final String AUTHOR_ID_NOT_EXIST = "idAuthor5";

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private Mongock mongock;

    @BeforeEach
    public void init() {
        mongock.execute();
    }

    @DisplayName("добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {
        val author = Author.builder()
                .authorName(AUTHOR_NEW_NAME)
                .build();
        String newId = authorRepository.save(author).getAuthorId();
        author.setAuthorId(newId);
        val actual = authorRepository.findById(newId).orElse(null);
        assertThat(actual).isNotNull().isEqualTo(author);
    }

    @DisplayName("изменять наименование автора")
    @Test
    void shouldUpdateAuthorName() {
        val author1 = authorRepository.findById(AUTHOR_ID_EXIST).orElse(null);
        assertNotNull(author1);
        String oldName = author1.getAuthorName();
        author1.setAuthorName(AUTHOR_NEW_NAME);
        Author author2 = authorRepository.save(author1);
        assertThat(author2.getAuthorName()).isNotEqualTo(oldName).isEqualTo(AUTHOR_NEW_NAME);
    }

    @DisplayName("удалять автора")
    @Test
    void shouldDeleteAuthor() {
        val author1 = authorRepository.findById(AUTHOR_ID_EXIST).orElse(null);
        assertThat(author1).isNotNull();
        authorRepository.deleteById(AUTHOR_ID_EXIST);
        val author2 = authorRepository.findById(AUTHOR_ID_EXIST).orElse(null);
        assertThat(author2).isNull();
    }

    @DisplayName("возвращать автора по его id")
    @Test
    void shouldFindAuthorById() {
        Optional<Author> author = authorRepository.findById(AUTHOR_ID_EXIST);
        assertThat(author).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("authorName", AUTHOR_NAME_EXISTS);
    }

    @DisplayName("возвращать список авторов по имени")
    @Test
    void shouldFindAuthorByName() {
        assertAll("Список авторов по имени",
                () -> assertThat(authorRepository.findByAuthorName(AUTHOR_NAME_EXISTS)).isNotNull().hasSize(1),
                () -> assertThat(authorRepository.findByAuthorName(AUTHOR_NAME_NOT_EXISTS)).isNotNull().hasSize(0)
        );
    }

    @DisplayName("возвращать список всех авторов")
    @Test
    void shouldFindAuthorAll() {
        List<Author> authors = authorRepository.findAll();
        assertThat(authors).isNotNull().hasSize(AUTHORS_COUNT_INT);
    }

    @DisplayName("возвращать количество авторов")
    @Test
    void shouldReturnAuthorCount() {
        long authorsCount = authorRepository.count();
        assertThat(authorsCount).isEqualTo(AUTHORS_COUNT_LONG);
    }

    @DisplayName("проверять наличие автора по идентификатору")
    @Test
    void shouldCheckExistById() {
        assertAll("Существование id автора",
                () -> assertThat(authorRepository.existsById(AUTHOR_ID_EXIST)).isEqualTo(true),
                () -> assertThat(authorRepository.existsById(AUTHOR_ID_NOT_EXIST)).isEqualTo(false)
        );
    }

    @DisplayName("проверять наличие автора по имени")
    @Test
    void shouldCheckExistByName() {
        assertAll("Существование имени автора",
                () -> assertThat(authorRepository.existsByAuthorName(AUTHOR_NAME_EXISTS)).isEqualTo(true),
                () -> assertThat(authorRepository.existsByAuthorName(AUTHOR_NAME_NOT_EXISTS)).isEqualTo(false)
        );
    }
}