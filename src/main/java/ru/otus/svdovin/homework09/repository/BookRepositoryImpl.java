package ru.otus.svdovin.homework09.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.svdovin.homework09.domain.Book;
import ru.otus.svdovin.homework09.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long insert(Book book) {
        em.persist(book);
        return book.getBookId();
    }

    @Override
    public Book update(Book book) {
        em.merge(book);
        return book;
    }

    @Override
    public void deleteById(long id) {
        Book book = em.find(Book.class, id);
        em.remove(book);
    }

    @Override
    public Optional<Book> getById(long id) {
        Book book = em.find(Book.class, id);
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> getByName(String name) {
        return em.createQuery("select b from Book b where b.bookName = :name", Book.class)
                .setParameter("name", name).getResultList();
    }

    @Override
    public List<Book> getByAuthorId(long authorId) {
        return em.createQuery("select b from Book b where b.author.authorId = :id order by b.bookId asc", Book.class)
                .setParameter("id", authorId).getResultList();
    }

    @Override
    public List<Book> getByGenreId(long genreId) {
        return em.createQuery("select b from Book b where b.genre.genreId = :id order by b.bookId asc", Book.class)
                .setParameter("id", genreId).getResultList();
    }

    @Override
    public List<Book> getAll() {
        return em.createQuery("select b from Book b join fetch b.author join fetch b.genre", Book.class).getResultList();
    }

    @Override
    public long count() {
        return em.createQuery("select count(b) from Book b", Long.class).getSingleResult();
    }

    @Override
    public boolean existsById(long id) {
        return em.createQuery("select count(b) > 0 from Book b where b.id = :id", Boolean.class)
                .setParameter("id", id).getSingleResult();
    }

    @Override
    public boolean existsByName(String name) {
        return em.createQuery("select count(b) > 0 from Book b where b.bookName = :name", Boolean.class)
                .setParameter("name", name).getSingleResult();
    }

    @Override
    public List<Comment> getCommentsByBookId(long id) {
        Book book = em.createQuery("select b from Book b " +
                "join fetch b.comments join fetch b.author join fetch b.genre where b.bookId = :id ", Book.class)
                .setParameter("id", id).getSingleResult();
        return book.getComments();
    }
}
