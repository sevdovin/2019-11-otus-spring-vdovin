package ru.otus.svdovin.homework09.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.svdovin.homework09.domain.Author;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long insert(Author author) {
        em.persist(author);
        return author.getAuthorId();
    }

    @Override
    public Author update(Author author) {
        em.merge(author);
        return author;
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Author a " +
                "where a.authorId = :id")
                .setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Optional<Author> getById(long id) {
        TypedQuery<Author> query = em.createQuery("select a from Author a " +
                "where a.authorId = :id", Author.class)
                .setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Author> getByName(String name) {
        return em.createQuery("select a from Author a " +
                "where a.authorName = :name", Author.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public List<Author> getAll() {
        return em.createQuery("select a from Author a", Author.class)
                .getResultList();
    }

    @Override
    public long count() {
        return em.createQuery("select count(a) from Author a", Long.class)
                .getSingleResult();
    }

    @Override
    public boolean existsById(long id) {
        return em.createQuery("select count(a) > 0 from Author a where a.id = :id", Boolean.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public boolean existsByName(String name) {
        return em.createQuery("select count(a) > 0 from Author a " +
                "where a.authorName = :name", Boolean.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
