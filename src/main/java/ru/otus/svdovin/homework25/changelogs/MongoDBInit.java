package ru.otus.svdovin.homework25.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.svdovin.homework25.domain.mongo.Author;
import ru.otus.svdovin.homework25.domain.mongo.Book;
import ru.otus.svdovin.homework25.domain.mongo.Comment;
import ru.otus.svdovin.homework25.domain.mongo.Genre;

import java.util.Arrays;

@ChangeLog(order = "001")
public class MongoDBInit {
    private Author author1;
    private Author author2;
    private Author author3;
    private Author author4;

    private Genre genre1;
    private Genre genre2;
    private Genre genre3;
    private Genre genre4;
    private Genre genre5;

    private Book book1;

    @ChangeSet(order = "000", id = "dropDB", author = "svdovin", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "insertAuthors", author = "svdovin", runAlways = true)
    public void insertAuthors(MongoTemplate template) {
        author1 = template.save(new Author(1, "Лев Толстой"));
        author2 = template.save(new Author(2, "Александр Пушкин"));
        author3 = template.save(new Author(3, "Уильям Шекспир"));
        author4 = template.save(new Author(4, "Антон Чехов"));
        template.save(new Author(5, "Михаил Лермонтов"));
    }

    @ChangeSet(order = "002", id = "insertGenres", author = "svdovin", runAlways = true)
    public void insertGenres(MongoTemplate template) {
        genre1 = template.save(new Genre(1, "Роман"));
        genre2 = template.save(new Genre(2, "Сказка"));
        genre3 = template.save(new Genre(3, "Комедия"));
        genre4 = template.save(new Genre(4, "Трагедия"));
        genre5 = template.save(new Genre(5, "Пьеса"));
    }

    @ChangeSet(order = "003", id = "insertBooks", author = "svdovin", runAlways = true)
    public void insertBooks(MongoTemplate template) {
        book1 = template.save(new Book(1, "Война и мир", genre1, author1));
        template.save(new Book(2, "Лукоморье", genre2, author2));
        template.save(new Book(3, "Евгений Онегин", genre1, author2));
        template.save(new Book(4, "Много шума из ничего", genre3, author3));
        template.save(new Book(5, "Отелло", genre4, author3));
        template.save(new Book(6, "Вишневый сад", genre5, author4));
    }

    @ChangeSet(order = "004", id = "insertComments", author = "svdovin", runAlways = true)
    public void insertComments(MongoTemplate template) {
        template.save(new Comment(1, "Роман", book1));
        template.save(new Comment(2, "Сказка", book1));
    }
}
