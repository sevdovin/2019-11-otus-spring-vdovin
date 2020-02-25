package ru.otus.svdovin.homework07.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.svdovin.homework07.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public GenreDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public long insert(Genre genre) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", genre.getGenreName());
        KeyHolder kh = new GeneratedKeyHolder();
        namedParameterJdbcOperations.update("insert into genres (`name`) values (:name)", params, kh);
        return kh.getKey().longValue();

    }

    @Override
    public void update(Genre genre) {
        final Map<String, Object> params = new HashMap<>(2);
        params.put("id", genre.getGenreId());
        params.put("name", genre.getGenreName());
        namedParameterJdbcOperations.update("update genres set name = :name where id = :id", params);
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update("delete from genres where id = :id", params);
    }

    @Override
    public Optional<Genre> getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            return Optional.ofNullable(namedParameterJdbcOperations.queryForObject(
                    "select * from genres where id = :id", params, new GenreDaoJdbc.GenreMapper()
            ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Genre> getByName(String name) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("name", name);
        return namedParameterJdbcOperations.query(
                "select * from genres where name = :name", params, new GenreDaoJdbc.GenreMapper()
        );
    }

    @Override
    public List<Genre> getAll() {
        return namedParameterJdbcOperations.query("select * from genres", new GenreDaoJdbc.GenreMapper());
    }

    @Override
    public int count() {
        MapSqlParameterSource params = new MapSqlParameterSource();
        return namedParameterJdbcOperations.queryForObject("select count(*) from genres", params, Integer.class);
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
