package ru.otus.svdovin.homework29.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.otus.svdovin.homework29.repository.AuthorRepository;
import ru.otus.svdovin.homework29.repository.BookRepository;
import ru.otus.svdovin.homework29.repository.CommentRepository;
import ru.otus.svdovin.homework29.repository.GenreRepository;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class DataHealthIndicator implements HealthIndicator {

    private static final String ERROR = "error";
    private static final String DATA_ERROR_MESSAGE = "Data is absent in db";

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @Override
    public Health health() {
        Map<String, Object> details = getDetails();
        if (!details.isEmpty()) {
            return Health.up()
                    .status(Status.UP)
                    .withDetails(details)
                    .build();
        } else {
            return Health.outOfService()
                    .status(Status.OUT_OF_SERVICE)
                    .withDetail(ERROR, DATA_ERROR_MESSAGE)
                    .build();
        }
    }

    private Map<String, Object> getDetails() {
        Map<String, Object> details = new HashMap<>();

        long authorCount = authorRepository.count();
        if (authorCount > 0) {
            details.put("authorCount", authorCount);
        }

        long genreCount = genreRepository.count();
        if (genreCount > 0) {
            details.put("genreCount", genreCount);
        }

        long bookCount = bookRepository.count();
        if (bookCount > 0) {
            details.put("bookCount", bookCount);
        }

        long commentCount = commentRepository.count();
        if (commentCount > 0) {
            details.put("commentCount", commentCount);
        }

        return details;
    }
}
