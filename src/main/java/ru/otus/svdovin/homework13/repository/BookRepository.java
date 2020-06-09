package ru.otus.svdovin.homework13.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.svdovin.homework13.domain.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {

    Optional<Book> findById(String bookId);

    List<Book> findByBookName(String bookName);

    List<Book> findAll();

    List<Book> findByAuthorsAuthorId(String authorId);

    List<Book> findByGenreGenreId(String genreId);

    boolean existsByBookName(String bookName);
}

