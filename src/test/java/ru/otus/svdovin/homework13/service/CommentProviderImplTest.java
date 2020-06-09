package ru.otus.svdovin.homework13.service;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.svdovin.homework13.domain.Comment;
import ru.otus.svdovin.homework13.repository.CommentRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Сервис для работы с комментариями ")
@SpringBootTest(classes = {CommentProviderImpl.class})
class CommentProviderImplTest {

    private static final String NEW_COMMENT_ID = "idComment3";
    private static final String NEW_COMMENT_COMMENT = "Comment 3-test";
    private static final String DEFAULT_COMMENT_ID = "idComment1";
    private static final String DEFAULT_COMMENT_ID_NOT_EXISTS = "idComment3";
    private static final String DEFAULT_BOOK_ID = "idBook1";
    private static final String DEFAULT_BOOK_ID_NOT_EXISTS = "idBook2";

    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private CommentProvider commentProvider;

    @Test
    @DisplayName("должен корректно добавлять комментарий")
    void shouldCeateComment() {
        val comment = Comment.builder()
                .commentId(NEW_COMMENT_ID)
                .comment(NEW_COMMENT_COMMENT)
                .build();
        Mockito.when(commentRepository.save(comment)).thenReturn(comment);
        String newId = commentProvider.createComment(comment);
        assertThat(newId).isEqualTo(NEW_COMMENT_ID);
    }

    @Test
    @DisplayName("удалять ожидаемый комментарий")
    void shouldDeleteExpectedCommentById() {
        commentProvider.deleteCommentById(DEFAULT_COMMENT_ID);
        verify(commentRepository, times(1)).deleteById(DEFAULT_COMMENT_ID);
    }

    @Test
    @DisplayName("возвращать ожидаемый комментарий по его id")
    void shouldReturnExpectedCommentById() {
        val comment = Comment.builder()
                .commentId(NEW_COMMENT_ID)
                .comment(NEW_COMMENT_COMMENT)
                .build();
        Mockito.when(commentRepository.findById(NEW_COMMENT_ID)).thenReturn(Optional.of(comment));
        Comment actual = commentProvider.getCommentById(NEW_COMMENT_ID).orElse(null);
        assertThat(actual).isEqualToComparingFieldByField(comment);
    }

    @Test
    @DisplayName("возвращать ожидаемый список комментариев по id книги")
    void shouldReturnExpectedCommentsByBookId() {
        val comment = Comment.builder()
                .commentId(NEW_COMMENT_ID)
                .comment(NEW_COMMENT_COMMENT)
                .build();
        List<Comment> comments = Collections.singletonList(new Comment());
        Mockito.when(commentRepository.findByBookBookId(DEFAULT_BOOK_ID)).thenReturn(comments);
        List<Comment> actual = commentProvider.getCommentsByBookId(DEFAULT_BOOK_ID);
        assertThat(actual).isEqualTo(comments);
    }

    @Test
    @DisplayName("проверять наличие комментария по идентификатору")
    void shouldCheckCommentExistsById() {
        Mockito.when(commentRepository.existsById(DEFAULT_COMMENT_ID)).thenReturn(true);
        Mockito.when(commentRepository.existsById(DEFAULT_COMMENT_ID_NOT_EXISTS)).thenReturn(false);
        assertAll("Существование id комментария",
                () -> assertThat(commentProvider.existsCommentById(DEFAULT_COMMENT_ID)).isEqualTo(true),
                () -> assertThat(commentProvider.existsCommentById(DEFAULT_COMMENT_ID_NOT_EXISTS)).isEqualTo(false)
        );
    }

    @Test
    @DisplayName("проверять наличие комментариев по идентификатору книги")
    void shouldCheckCommentExistsByBookId() {
        Mockito.when(commentRepository.existsByBookBookId(DEFAULT_BOOK_ID)).thenReturn(true);
        Mockito.when(commentRepository.existsByBookBookId(DEFAULT_BOOK_ID_NOT_EXISTS)).thenReturn(false);
        assertAll("Существование комментариев по id книги",
                () -> assertThat(commentProvider.existsCommentByBookId(DEFAULT_BOOK_ID)).isEqualTo(true),
                () -> assertThat(commentProvider.existsCommentByBookId(DEFAULT_BOOK_ID_NOT_EXISTS)).isEqualTo(false)
        );
    }

}
