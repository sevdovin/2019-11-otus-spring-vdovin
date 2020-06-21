package ru.otus.svdovin.homework16.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.svdovin.homework16.domain.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c join fetch c.book b join fetch b.authors where c.book.bookId = :bookId order by c.id asc")
    List<Comment> findByBookBookId(Long bookId);

    boolean existsByBookBookId(Long bookId);
}
