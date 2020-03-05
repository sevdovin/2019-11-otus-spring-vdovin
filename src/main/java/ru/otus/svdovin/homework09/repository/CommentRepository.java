package ru.otus.svdovin.homework09.repository;

import ru.otus.svdovin.homework09.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    long insert(Comment comment);
    void deleteById(long id);
    Optional<Comment> getById(long id);
    List<Comment> getByBookId(long bookId);
    boolean existsById(long id);
    boolean existsByBookId(long bookId);
}
