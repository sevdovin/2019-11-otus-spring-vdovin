package ru.otus.svdovin.homework09.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.svdovin.homework09.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class CommentRepositoryImpl implements CommentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long insert(Comment comment) {
        em.persist(comment);
        return comment.getCommentId();
    }

    @Override
    public void deleteById(long id) {
        Comment comment = em.find(Comment.class, id);
        em.remove(comment);
    }

    @Override
    public Optional<Comment> getById(long id) {
        Comment comment = em.find(Comment.class, id);
        return Optional.ofNullable(comment);
    }

    @Override
    public List<Comment> getByBookId(long bookId) {
        return em.createQuery("select c from Comment c where c.book.bookId = :id order by c.id asc", Comment.class)
                .setParameter("id", bookId).getResultList();
    }

    @Override
    public boolean existsById(long id) {
        return em.createQuery("select count(c) > 0 from Comment c where c.id = :id", Boolean.class)
                .setParameter("id", id).getSingleResult();
    }

    @Override
    public boolean existsByBookId(long bookId) {
        return em.createQuery("select count(c) > 0 from Comment c where c.book.bookId = :id", Boolean.class)
                .setParameter("id", bookId).getSingleResult();
    }
}
