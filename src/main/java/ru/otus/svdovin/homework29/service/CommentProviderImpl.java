package ru.otus.svdovin.homework29.service;

import org.springframework.stereotype.Service;
import ru.otus.svdovin.homework29.domain.Comment;
import ru.otus.svdovin.homework29.repository.CommentRepository;

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
        return commentRepository.save(comment).getCommentId();
    }

    @Override
    public void deleteCommentById(long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Optional<Comment> getCommentById(long id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> getCommentsByBookId(long bookId) {
        return commentRepository.findByBookBookId(bookId);
    }

    @Override
    public boolean existsCommentById(long id) {
        return commentRepository.existsById(id);
    }

    @Override
    public boolean existsCommentByBookId(long bookId) {
        return commentRepository.existsByBookBookId(bookId);
    }
}
