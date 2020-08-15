package ru.otus.svdovin.homework29.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.otus.svdovin.homework29.domain.Author;

import java.util.List;

@Repository
@RepositoryRestResource(path = "authors")
public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findByAuthorName(String authorName);

    boolean existsByAuthorName(String authorName);
}
