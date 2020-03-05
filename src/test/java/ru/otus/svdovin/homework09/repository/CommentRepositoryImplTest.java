package ru.otus.svdovin.homework09.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.svdovin.homework09.domain.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями должен ")
@DataJpaTest
@Import(CommentRepositoryImpl.class)
class CommentRepositoryImplTest {

    private static final String TEST_COMMENT = "Comment 1-test";
    private static final long TEST_COMMENT_ID = 1;
    private static final long TEST_COMMENT_ID_NOT_EXISTS = 3;
    private static final String TEST_COMMENT_1 = "Comment 1-1";
    private static final long TEST_BOOK_ID = 1;
    private static final long TEST_BOOK_ID_NOT_EXISTS = 2;
    private static final int TEST_BOOK_ID_COMMENT_COUNT = 2;

    @Autowired
    private CommentRepositoryImpl commentRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("добавлять комментарий в БД")
    @Test
    void shouldInsertComment() {
        val comment = new Comment(0, TEST_COMMENT);
        long newId = commentRepository.insert(comment);
        comment.setCommentId(newId);
        val actual = em.find(Comment.class, newId);
        assertThat(actual).isNotNull().isEqualToComparingFieldByField(comment);
    }

    @DisplayName("удалять комментаритй")
    @Test
    void shouldDeleteComment() {
        Comment comment1 = em.find(Comment.class, TEST_COMMENT_ID);
        commentRepository.deleteById(TEST_COMMENT_ID);
        assertAll("Комментарий удален",
                () -> assertThat(comment1).isNotNull(),
                () -> assertThat(em.find(Comment.class, TEST_COMMENT_ID)).isNull()
        );
    }

    @DisplayName("возвращать комментарий по его id")
    @Test
    void shouldFindCommentById() {
        Optional<Comment> comment = commentRepository.getById(TEST_COMMENT_ID);
        assertThat(comment).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("comment", TEST_COMMENT_1);
    }

    @DisplayName("возвращать список комментариев по id книги")
    @Test
    void testGetCommentsByBookId() {
        List<Comment> commentList = commentRepository.getByBookId(TEST_BOOK_ID);
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
                () -> assertThat(commentRepository.existsByBookId(TEST_BOOK_ID)).isEqualTo(true),
                () -> assertThat(commentRepository.existsByBookId(TEST_BOOK_ID_NOT_EXISTS)).isEqualTo(false)
        );
    }
}