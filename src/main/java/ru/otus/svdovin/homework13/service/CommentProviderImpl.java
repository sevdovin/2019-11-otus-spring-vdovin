package ru.otus.svdovin.homework13.service;

import org.springframework.stereotype.Service;
import ru.otus.svdovin.homework13.domain.Comment;
import ru.otus.svdovin.homework13.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentProviderImpl implements CommentProvider {

    private final CommentRepository commentRepository;

    public CommentProviderImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public String createComment(Comment comment) {
        return commentRepository.save(comment).getCommentId();
    }

    @Override
    public void deleteCommentById(String id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Optional<Comment> getCommentById(String id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> getCommentsByBookId(String bookId) {
        return commentRepository.findByBookBookId(bookId);
    }

    @Override
    public boolean existsCommentById(String id) {
        return commentRepository.existsById(id);
    }

    @Override
    public boolean existsCommentByBookId(String bookId) {
        return commentRepository.existsByBookBookId(bookId);
    }
}
