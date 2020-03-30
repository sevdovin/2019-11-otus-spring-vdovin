package ru.otus.svdovin.homework09.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.svdovin.homework09.domain.Author;
import ru.otus.svdovin.homework09.domain.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long insert(Book book) {
        boolean authorExists = false;
        for (Author author : book.getAuthors()) {
            if (author.getAuthorId() > 0) {
                authorExists = true;
                break;
            }
        }
        if (!authorExists) {
            em.persist(book);
        } else {
            book = em.merge(book);
        }
        return book.getBookId();
    }

    @Override
    public Book update(Book book) {
        em.merge(book);
        return book;
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Book b " +
                "where b.bookId = :id")
                .setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Optional<Book> getById(long id) {
        TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.authors join fetch b.genre " +
                "where b.bookId = :id", Book.class)
                .setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getByName(String name) {
        return em.createQuery("select b from Book b join fetch b.authors " +
                "where b.bookName = :name", Book.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public List<Book> getByAuthorId(long authorId) {
        return em.createQuery("select b from Book b join fetch b.authors a " +
                "where a.authorId = :id " +
                "order by b.bookId asc", Book.class)
                .setParameter("id", authorId)
                .getResultList();
    }

    @Override
    public List<Book> getByGenreId(long genreId) {
        return em.createQuery("select b from Book b join fetch b.authors " +
                "where b.genre.genreId = :id " +
                "order by b.bookId asc", Book.class)
                .setParameter("id", genreId)
                .getResultList();
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = em.createQuery("select b from Book b join fetch b.genre", Book.class)
                .getResultList();
        if (!books.isEmpty()) {
            books.get(0).getAuthors().isEmpty();
        }
        return books;
    }

    @Override
    public long count() {
        return em.createQuery("select count(b) from Book b", Long.class)
                .getSingleResult();
    }

    @Override
    public boolean existsById(long id) {
        return em.createQuery("select count(b) > 0 from Book b " +
                "where b.id = :id", Boolean.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public boolean existsByName(String name) {
        return em.createQuery("select count(b) > 0 from Book b " +
                "where b.bookName = :name", Boolean.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
