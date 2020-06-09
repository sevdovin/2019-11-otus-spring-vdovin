package ru.otus.svdovin.homework13.repository;

import com.github.cloudyrock.mongock.Mongock;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.svdovin.homework13.domain.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями должен ")
@DataMongoTest
@ComponentScan({"ru.otus.svdovin.homework13.testdata"})
class CommentRepositoryImplTest {

    private static final String TEST_COMMENT = "Comment 1-test";
    private static final String TEST_BOOK_ID = "idBook1";
    private static final String COMMENT_ID_EXIST = "idComment1";
    private static final String TEST_COMMENT_1 = "Comment 1-1";
    private static final int TEST_BOOK_ID_COMMENT_COUNT = 2;
    private static final String COMMENT_ID_NOT_EXIST = "idComment3";
    private static final String TEST_BOOK_ID_NOT_EXISTS = "idBook5";

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private Mongock mongock;

    @BeforeEach
    public void init() {
        mongock.execute();
    }

    @DisplayName("добавлять комментарий в БД")
    @Test
    void shouldInsertComment() {
        val comment = Comment.builder()
                .comment(TEST_COMMENT)
                .build();
        String newId = commentRepository.save(comment).getCommentId();
        comment.setCommentId(newId);
        val actual = commentRepository.findById(newId).orElse(null);
        assertThat(actual).isNotNull().isEqualTo(comment);
    }

    @DisplayName("удалять комментарий")
    @Test
    void shouldDeleteComment() {
        val comment1 = commentRepository.findById(COMMENT_ID_EXIST).orElse(null);
        assertThat(comment1).isNotNull();
        commentRepository.deleteById(COMMENT_ID_EXIST);
        val comment2 = commentRepository.findById(COMMENT_ID_EXIST).orElse(null);
        assertThat(comment2).isNull();
    }

    @DisplayName("возвращать комментарий по его id")
    @Test
    void shouldFindCommentById() {
        Optional<Comment> comment = commentRepository.findById(COMMENT_ID_EXIST);
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
                () -> assertThat(commentRepository.existsById(COMMENT_ID_EXIST)).isEqualTo(true),
                () -> assertThat(commentRepository.existsById(COMMENT_ID_NOT_EXIST)).isEqualTo(false)
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