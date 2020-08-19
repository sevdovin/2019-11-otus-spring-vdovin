package ru.otus.svdovin.homework33.service;

import ru.otus.svdovin.homework33.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentProvider {
    long createComment(Comment comment);
    void deleteCommentById(long id);
    Optional<Comment> getCommentById(long id);
    List<Comment> getCommentsByBookId(long bookId);
    boolean existsCommentById(long id);
    boolean existsCommentByBookId(long bookId);
}
