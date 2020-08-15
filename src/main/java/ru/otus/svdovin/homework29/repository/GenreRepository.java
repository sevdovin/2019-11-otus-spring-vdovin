package ru.otus.svdovin.homework29.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.otus.svdovin.homework29.domain.Genre;

import java.util.List;

@Repository
@RepositoryRestResource(path = "genres")
public interface GenreRepository extends JpaRepository<Genre, Long> {

    List<Genre> findByGenreName(String genreName);

    boolean existsByGenreName(String genreName);
}
