package ru.otus.svdovin.homework13.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.svdovin.homework13.domain.Book;
import ru.otus.svdovin.homework13.domain.Genre;
import ru.otus.svdovin.homework13.repository.AuthorRepository;
import ru.otus.svdovin.homework13.repository.GenreRepository;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MongoBookCascadeSaveEventsListener extends AbstractMongoEventListener<Book> {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Book> event) {
        super.onBeforeConvert(event);
        val book = event.getSource();
        if (book.getAuthors() != null) {
            book.getAuthors().stream().filter(e -> Objects.isNull(e.getAuthorId())).forEach(authorRepository::save);
        }
        if (book.getGenre() != null) {
            Genre genre = book.getGenre();
            if (genre.getGenreId() == null) {
                genreRepository.save(genre);
            }
        }
    }
}
