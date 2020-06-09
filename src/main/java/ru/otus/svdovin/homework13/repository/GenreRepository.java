package ru.otus.svdovin.homework13.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.svdovin.homework13.domain.Genre;

import java.util.List;

@Repository
public interface GenreRepository extends MongoRepository<Genre, String> {

    List<Genre> findByGenreName(String genreName);

    boolean existsByGenreName(String genreName);
}
