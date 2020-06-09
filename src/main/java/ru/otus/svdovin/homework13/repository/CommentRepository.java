package ru.otus.svdovin.homework13.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.svdovin.homework13.domain.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findByBookBookId(String bookId);

    void deleteByBookBookId(String id);

    boolean existsByBookBookId(String bookId);
}
