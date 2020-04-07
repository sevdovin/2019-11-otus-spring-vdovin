package ru.otus.svdovin.homework11.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.svdovin.homework11.domain.Book;
import ru.otus.svdovin.homework11.domain.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями должен ")
@DataJpaTest
class CommentRepositoryImplTest {

    private static final String TEST_COMMENT = "Comment 1-test";
    private static final long TEST_COMMENT_ID = 1;
    private static final long TEST_COMMENT_ID_NOT_EXISTS = 3;
    private static final String TEST_COMMENT_1 = "Comment 1-1";
    private static final long TEST_BOOK_ID = 1;
    private static final long TEST_BOOK_ID_NOT_EXISTS = 2;
    private static final int TEST_BOOK_ID_COMMENT_COUNT = 2;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("добавлять комментарий в БД")
    @Test
    void shouldInsertComment() {
        val book = em.find(Book.class, TEST_BOOK_ID);
        val comment = new Comment(0, TEST_COMMENT, book);
        long newId = commentRepository.save(comment).getCommentId();
        val actual = em.find(Comment.class, newId);
        assertThat(actual).isNotNull().isEqualToComparingFieldByField(comment);
    }

    @DisplayName("удалять комментарий")
    @Test
    void shouldDeleteComment() {
        val comment1 = em.find(Comment.class, TEST_COMMENT_ID);
        assertThat(comment1).isNotNull();
        em.detach(comment1);
        commentRepository.deleteById(TEST_COMMENT_ID);
        val comment2 = em.find(Comment.class, TEST_COMMENT_ID);
        assertThat(comment2).isNull();
    }

    @DisplayName("возвращать комментарий по его id")
    @Test
    void shouldFindCommentById() {
        Optional<Comment> comment = commentRepository.findById(TEST_COMMENT_ID);
        assertThat(comment).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("comment", TEST_COMMENT_1);
    }

    @DisplayName("возвращать список комментариев по id книги")
    @Test
    void testGetCommentsByBookId() {
        List<Comment> commentList = commentRepository.findByBookBookId(TEST_BOOK_ID);
        assertThat(commentList).isNotNull().hasSize(TEST_BOOK_ID_COMMENT_COUNT);
    }

    @DisplayName("проверять наличие ккомментария по идентификатору")
    @Test
    void shouldCheckExistById() {
        assertAll("Существование id комментария",
                () -> assertThat(commentRepository.existsById(TEST_COMMENT_ID)).isEqualTo(true),
                () -> assertThat(commentRepository.existsById(TEST_COMMENT_ID_NOT_EXISTS)).isEqualTo(false)
        );
    }

    @DisplayName("проверять наличие ккомментария по идентификатору книги")
    @Test
    void shouldCheckExistByBookId() {
        assertAll("Существование комментария по id книги",
                () -> assertThat(commentRepository.existsByBookBookId(TEST_BOOK_ID)).isEqualTo(true),
                () -> assertThat(commentRepository.existsByBookBookId(TEST_BOOK_ID_NOT_EXISTS)).isEqualTo(false)
        );
    }
}