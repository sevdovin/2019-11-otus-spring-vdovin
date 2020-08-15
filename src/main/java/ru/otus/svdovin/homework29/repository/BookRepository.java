package ru.otus.svdovin.homework29.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.otus.svdovin.homework29.domain.Book;

import java.util.List;
import java.util.Optional;

@Repository
@RepositoryRestResource(path = "books")
public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(value = "BookWithAuthorAndGenre")
    Optional<Book> findById(Long bookId);

    @EntityGraph(value = "BookWithAuthorAndGenre")
    List<Book> findByBookName(String bookName);

    @EntityGraph(value = "BookWithAuthorAndGenre")
    List<Book> findAll();

    @EntityGraph(value = "BookWithAuthorAndGenre")
    List<Book> findByAuthorsAuthorId(Long authorId);

    @EntityGraph(value = "BookWithAuthorAndGenre")
    List<Book> findByGenreGenreId(Long genreId);

    boolean existsByBookName(String bookName);
}

