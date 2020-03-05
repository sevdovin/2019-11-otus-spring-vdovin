package ru.otus.svdovin.homework09.service;

import org.springframework.stereotype.Service;
import ru.otus.svdovin.homework09.domain.Comment;
import ru.otus.svdovin.homework09.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentProviderImpl implements CommentProvider {

    private final CommentRepository commentRepository;

    public CommentProviderImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public long createComment(Comment comment) {
        return commentRepository.insert(comment);
    }

    @Override
    public void deleteCommentById(long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Optional<Comment> getCommentById(long id) {
        return commentRepository.getById(id);
    }

    @Override
    public List<Comment> getCommentsByBookId(long bookId) {
        return commentRepository.getByBookId(bookId);
    }

    @Override
    public boolean existsCommentById(long id) {
        return commentRepository.existsById(id);
    }

    @Override
    public boolean existsCommentByBookId(long bookId) {
        return commentRepository.existsByBookId(bookId);
    }
}
