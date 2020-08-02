package ru.otus.svdovin.homework20.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.otus.svdovin.homework20.domain.Book;
import ru.otus.svdovin.homework20.repository.CommentRepository;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class MongoBookCascadeDeleteEventsListener extends AbstractMongoEventListener<Book> {

    private final CommentRepository commentRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Book> event) {
        super.onAfterDelete(event);
        val source = event.getSource();
        val id = source.get("bookId").toString();
        commentRepository.deleteByBookBookId(id).subscribe();
    }
}
