package ru.otus.svdovin.homework07.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.svdovin.homework07.domain.Author;
import ru.otus.svdovin.homework07.domain.Book;
import ru.otus.svdovin.homework07.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public long insert(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", book.getBookName());
        params.addValue("authorId", book.getAuthor().getAuthorId());
        params.addValue("genreId", book.getGenre().getGenreId());
        KeyHolder kh = new GeneratedKeyHolder();
        namedParameterJdbcOperations.update("insert into books (`name`, `authorId`, `genreId`) values (:name, :authorId, :genreId)", params, kh);
        return kh.getKey().longValue();
    }

    @Override
    public void update(Book book) {
        final Map<String, Object> params = new HashMap<>(4);
        params.put("id", book.getBookId());
        params.put("name", book.getBookName());
        params.put("authorId", book.getAuthor().getAuthorId());
        params.put("genreId", book.getGenre().getGenreId());
        namedParameterJdbcOperations.update("update books set name = :name, authorId = :authorId, genreId = :genreId where id = :id", params);
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update("delete from books where id = :id", params);
    }

    @Override
    public Optional<Book> getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            return Optional.ofNullable(namedParameterJdbcOperations.queryForObject(
                    "select b.id, b.name, b.authorId, a.name as authorName, b.genreId, g.name as genreName"
                            + "\n from books b"
                            + "\n inner join authors a on a.id = b.authorId"
                            + "\n inner join genres g on g.id = b.genreId"
                            + "\n where b.id = :id"
                    , params, new BookMapper()
            ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getByName(String name) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("name", name);
        return namedParameterJdbcOperations.query(
                "select b.id, b.name, b.authorId, a.name as authorName, b.genreId, g.name as genreName"
                        + "\n from books b"
                        + "\n inner join authors a on a.id = b.authorId"
                        + "\n inner join genres g on g.id = b.genreId"
                        + "\n where b.name = :name"
                , params, new BookMapper()
        );
    }

    @Override
    public List<Book> getByAuthorId(long authorId) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("authorId", authorId);
        return namedParameterJdbcOperations.query(
                "select b.id, b.name, b.authorId, a.name as authorName, b.genreId, g.name as genreName"
                        + "\n from books b"
                        + "\n inner join authors a on a.id = b.authorId"
                        + "\n inner join genres g on g.id = b.genreId"
                        + "\n where b.authorId = :authorId"
                , params, new BookMapper()
        );
    }

    @Override
    public List<Book> getByGenreId(long genreId) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("genreId", genreId);
        return namedParameterJdbcOperations.query(
                "select b.id, b.name, b.authorId, a.name as authorName, b.genreId, g.name as genreName"
                        + "\n from books b"
                        + "\n inner join authors a on a.id = b.authorId"
                        + "\n inner join genres g on g.id = b.genreId"
                        + "\n where b.genreId = :genreId"
                , params, new BookMapper()
        );
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.query(
                        "select b.id, b.name, b.authorId, a.name as authorName, b.genreId, g.name as genreName"
                                + "\n from books b"
                                + "\n inner join authors a on a.id = b.authorId"
                                + "\n inner join genres g on g.id = b.genreId"
                        , new BookMapper());
    }

    @Override
    public int count() {
        MapSqlParameterSource params = new MapSqlParameterSource();
        return namedParameterJdbcOperations.queryForObject("select count(*) from books", params, Integer.class);
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            long authorId = resultSet.getLong("authorId");
            String authorName = resultSet.getString("authorName");
            long genreId = resultSet.getLong("genreId");
            String genreName = resultSet.getString("genreName");
            Author author = new Author(authorId, authorName);
            Genre genre = new Genre(genreId, genreName);
            return new Book(id, name, author, genre);
        }
    }
}
