package ru.otus.svdovin.homework13.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.svdovin.homework13.domain.Author;
import ru.otus.svdovin.homework13.repository.BookRepository;

@Component
@RequiredArgsConstructor
public class MongoAuthorCascadeDeleteEventsListener extends AbstractMongoEventListener<Author> {

    private final BookRepository bookRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Author> event) {
        super.onAfterDelete(event);
        val source = event.getSource();
        val id = source.get("_id").toString();
        bookRepository.removeAuthorsArrayElementsById(id);
    }
}
