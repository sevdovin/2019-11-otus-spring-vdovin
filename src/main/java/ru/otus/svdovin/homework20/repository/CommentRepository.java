package ru.otus.svdovin.homework20.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.otus.svdovin.homework20.domain.Comment;

@Repository
public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {

    Mono<Void> deleteByBookBookId(String id);
}
