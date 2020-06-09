package ru.otus.svdovin.homework13.testdata;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.svdovin.homework13.domain.Author;
import ru.otus.svdovin.homework13.domain.Book;
import ru.otus.svdovin.homework13.domain.Comment;
import ru.otus.svdovin.homework13.domain.Genre;

import java.util.Arrays;

@ChangeLog(order = "001")
public class MongoDBInitTest {
    private Author author1;
    private Author author2;
    private Author author3;

    private Genre genre1;
    private Genre genre2;
    private Genre genre3;

    private Book book1;

    @ChangeSet(order = "000", id = "dropDB", author = "svdovin", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "insertAuthors", author = "svdovin", runAlways = true)
    public void insertAuthors(MongoTemplate template) {
        author1 = template.save(new Author("idAuthor1", "Author 1"));
        author2 = template.save(new Author("idAuthor2", "Author 2"));
        author3 = template.save(new Author("idAuthor3", "Author 3"));
        template.save(new Author("idAuthor4", "Author 4"));
    }

    @ChangeSet(order = "002", id = "insertGenres", author = "svdovin", runAlways = true)
    public void insertGenres(MongoTemplate template) {
        genre1 = template.save(new Genre("idGenre1", "Genre 1"));
        genre2 = template.save(new Genre("idGenre2", "Genre 2"));
        genre3 = template.save(new Genre("idGenre3", "Genre 3"));
        template.save(new Genre("idGenre4", "Genre 4"));
    }

    @ChangeSet(order = "003", id = "insertBooks", author = "svdovin", runAlways = true)
    public void insertBooks(MongoTemplate template) {
        book1 = template.save(new Book("idBook1", "Book 1", genre1, Arrays.asList(author1)));
        template.save(new Book("idBook2", "Book 2", genre1, Arrays.asList(author2)));
        template.save(new Book("idBook3", "Book 3", genre2, Arrays.asList(author2)));
        template.save(new Book("idBook4", "Book 4", genre1, Arrays.asList(author3)));
        template.save(new Book("idBook5", "Book 5", genre3, Arrays.asList(author3)));
    }

    @ChangeSet(order = "004", id = "insertComments", author = "svdovin", runAlways = true)
    public void insertComments(MongoTemplate template) {
        template.save(new Comment("idComment1", "Comment 1-1", book1));
        template.save(new Comment("idComment2", "Comment 1-2", book1));
    }
}
