package ru.otus.svdovin.homework09.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.svdovin.homework09.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Репозиторий на основе Jpa для работы с авторами должен ")
@DataJpaTest
@Import(AuthorRepositoryImpl.class)
class AuthorRepositoryImplTest {

    private static final String AUTHOR_NEW_NAME = "New Author";
    private static final long AUTHORS_COUNT_LONG = 4;
    private static final int AUTHORS_COUNT_INT = 4;
    private static final long AUTHOR_ID_EXIST = 4;
    private static final long AUTHOR_ID_NOT_EXIST = 5;
    private static final String AUTHOR_NAME_EXISTS = "Author 4";
    private static final String AUTHOR_NAME_NOT_EXISTS = "Author 5";

    @Autowired
    private AuthorRepositoryImpl authorRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {
        val author = new Author(0, AUTHOR_NEW_NAME);
        authorRepository.insert(author);
        assertThat(author.getAuthorId()).isGreaterThan(0);
        val actualAuthor = em.find(Author.class, author.getAuthorId());
        assertThat(actualAuthor).isNotNull().isEqualToComparingFieldByField(author);
    }

    @DisplayName("изменять наименование автора")
    @Test
    void shouldUpdateAuthorName() {
        Author author1 = em.find(Author.class, AUTHOR_ID_EXIST);
        String oldName = author1.getAuthorName();
        author1.setAuthorName(AUTHOR_NEW_NAME);
        Author author2 = authorRepository.update(author1);
        assertAll("Автор изменился",
                () -> assertThat(author2).isNotNull(),
                () -> assertThat(author2).isEqualToComparingFieldByField(author1),
                () -> assertThat(author2.getAuthorName()).isNotEqualTo(oldName)
        );
    }

    @DisplayName("удалять автора")
    @Test
    void shouldDeleteAuthor() {
        Author author1 = em.find(Author.class, AUTHOR_ID_EXIST);
        authorRepository.deleteById(AUTHOR_ID_EXIST);
        assertAll("Автор удален",
                () -> assertThat(author1).isNotNull(),
                () -> assertThat(em.find(Author.class, AUTHOR_ID_EXIST)).isNull()
        );
    }

    @DisplayName("возвращать автора по его id")
    @Test
    void shouldFindAuthorById() {
        Optional<Author> author = authorRepository.getById(AUTHOR_ID_EXIST);
        assertThat(author).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("authorName", AUTHOR_NAME_EXISTS);
    }

    @DisplayName("возвращать список авторов по имени")
    @Test
    void shouldFindAuthorByName() {
        assertAll("Список авторов по имени",
                () -> assertThat(authorRepository.getByName(AUTHOR_NAME_EXISTS)).isNotNull().hasSize(1),
                () -> assertThat(authorRepository.getByName(AUTHOR_NAME_NOT_EXISTS)).isNotNull().hasSize(0)
        );
    }

    @DisplayName("возвращать список всех авторов")
    @Test
    void shouldFindAuthorAll() {
        List<Author> authors = authorRepository.getAll();
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
                () -> assertThat(authorRepository.existsByName(AUTHOR_NAME_EXISTS)).isEqualTo(true),
                () -> assertThat(authorRepository.existsByName(AUTHOR_NAME_NOT_EXISTS)).isEqualTo(false)
        );
    }
}