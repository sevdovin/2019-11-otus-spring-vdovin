package ru.otus.svdovin.homework24.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.svdovin.homework24.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Репозиторий на основе Jpa для работы с авторами должен ")
@DataJpaTest
class AuthorRepositoryImplTest {

    private static final String AUTHOR_NEW_NAME = "New Author";
    private static final long AUTHORS_COUNT_LONG = 5;
    private static final int AUTHORS_COUNT_INT = 5;
    private static final long AUTHOR_ID_EXIST = 4;
    private static final long AUTHOR_ID_NOT_EXIST = 6;
    private static final String AUTHOR_NAME_EXISTS = "Author 4";
    private static final String AUTHOR_NAME_NOT_EXISTS = "Author 6";

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {
        val author = new Author(0, AUTHOR_NEW_NAME);
        long newId = authorRepository.save(author).getAuthorId();
        val actual = em.find(Author.class, newId);
        assertThat(actual).isNotNull().isEqualToComparingFieldByField(author);
    }

    @DisplayName("изменять наименование автора")
    @Test
    void shouldUpdateAuthorName() {
        Author author1 = em.find(Author.class, AUTHOR_ID_EXIST);
        String oldName = author1.getAuthorName();
        em.detach(author1);
        author1.setAuthorName(AUTHOR_NEW_NAME);
        Author author2 = authorRepository.save(author1);
        assertThat(author2.getAuthorName()).isNotEqualTo(oldName).isEqualTo(AUTHOR_NEW_NAME);
    }

    @DisplayName("удалять автора")
    @Test
    void shouldDeleteAuthor() {
        val author1 = em.find(Author.class, AUTHOR_ID_EXIST);
        assertThat(author1).isNotNull();
        em.detach(author1);
        authorRepository.deleteById(AUTHOR_ID_EXIST);
        val author2 = em.find(Author.class, AUTHOR_ID_EXIST);
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