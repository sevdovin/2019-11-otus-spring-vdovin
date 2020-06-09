package ru.otus.svdovin.homework13.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.svdovin.homework13.domain.Author;

import java.util.List;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {

    List<Author> findByAuthorName(String authorName);

    boolean existsByAuthorName(String authorName);
}
