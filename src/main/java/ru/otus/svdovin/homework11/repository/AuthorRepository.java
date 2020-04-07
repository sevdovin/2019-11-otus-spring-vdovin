package ru.otus.svdovin.homework11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.svdovin.homework11.domain.Author;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findByAuthorName(String authorName);

    boolean existsByAuthorName(String authorName);
}
