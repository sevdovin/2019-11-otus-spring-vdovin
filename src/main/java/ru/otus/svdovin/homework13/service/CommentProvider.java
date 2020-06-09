package ru.otus.svdovin.homework13.service;

import ru.otus.svdovin.homework13.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentProvider {
    String createComment(Comment comment);
    void deleteCommentById(String id);
    Optional<Comment> getCommentById(String id);
    List<Comment> getCommentsByBookId(String bookId);
    boolean existsCommentById(String id);
    boolean existsCommentByBookId(String bookId);
}
