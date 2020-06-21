package ru.otus.svdovin.homework16.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.svdovin.homework16.domain.Genre;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    List<Genre> findByGenreName(String genreName);

    boolean existsByGenreName(String genreName);
}
