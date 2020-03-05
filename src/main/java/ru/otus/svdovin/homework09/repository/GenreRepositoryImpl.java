package ru.otus.svdovin.homework09.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.svdovin.homework09.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class GenreRepositoryImpl implements GenreRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long insert(Genre genre) {
        em.persist(genre);
        return genre.getGenreId();
    }

    @Override
    public Genre update(Genre genre) {
        em.merge(genre);
        return genre;
    }

    @Override
    public void deleteById(long id) {
        Genre genre = em.find(Genre.class, id);
        em.remove(genre);
    }

    @Override
    public Optional<Genre> getById(long id) {
        Genre genre = em.find(Genre.class, id);
        return Optional.ofNullable(genre);
    }

    @Override
    public List<Genre> getByName(String name) {
        return em.createQuery("select g from Genre g where g.genreName = :name", Genre.class)
                .setParameter("name", name).getResultList();
    }

    @Override
    public List<Genre> getAll() {
        return em.createQuery("select g from Genre g", Genre.class).getResultList();
    }

    @Override
    public long count() {
        return em.createQuery("select count(g) from Genre g", Long.class).getSingleResult();
    }

    @Override
    public boolean existsById(long id) {
        return em.createQuery("select count(g) > 0 from Genre g where g.id = :id", Boolean.class)
                .setParameter("id", id).getSingleResult();
    }

    @Override
    public boolean existsByName(String name) {
        return em.createQuery("select count(g) > 0 from Genre g where g.genreName = :name", Boolean.class)
                .setParameter("name", name).getSingleResult();
    }
}
