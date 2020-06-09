package ru.otus.svdovin.homework13.changelogs;

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
        author1 = template.save(new Author("5edfdcce81099316f7254c91", "Лев Толстой"));
        author2 = template.save(new Author("5edfdcce81099316f7254c92", "Александр Пушкин"));
        author3 = template.save(new Author("5edfdcce81099316f7254c93", "Уильям Шекспир"));
        author4 = template.save(new Author("5edfdcce81099316f7254c94", "Антон Чехов"));
        template.save(new Author("5edfdcce81099316f7254c95", "Михаил Лермонтов"));
    }

    @ChangeSet(order = "002", id = "insertGenres", author = "svdovin", runAlways = true)
    public void insertGenres(MongoTemplate template) {
        genre1 = template.save(new Genre("5edfdcce81099316f7254c96", "Роман"));
        genre2 = template.save(new Genre("5edfdcce81099316f7254c97", "Сказка"));
        genre3 = template.save(new Genre("5edfdcce81099316f7254c98", "Комедия"));
        genre4 = template.save(new Genre("5edfdcce81099316f7254c99", "Трагедия"));
        genre5 = template.save(new Genre("5edfdcce81099316f7254c9a", "Пьеса"));
    }

    @ChangeSet(order = "003", id = "insertBooks", author = "svdovin", runAlways = true)
    public void insertBooks(MongoTemplate template) {
        book1 = template.save(new Book("5edfdcce81099316f7254c9b", "Война и мир", genre1, Arrays.asList(author1)));
        template.save(new Book("5edfdcce81099316f7254c9c", "Лукоморье", genre2, Arrays.asList(author2)));
        template.save(new Book("5edfdcce81099316f7254c9d", "Евгений Онегин", genre1, Arrays.asList(author2)));
        template.save(new Book("5edfdcce81099316f7254c9e", "Много шума из ничего", genre3, Arrays.asList(author3)));
        template.save(new Book("5edfdcce81099316f7254c9f", "Отелло", genre4, Arrays.asList(author3,author1)));
        template.save(new Book("5edfdcce81099316f7254ca0", "Вишневый сад", genre5, Arrays.asList(author4,author1)));
    }

    @ChangeSet(order = "004", id = "insertComments", author = "svdovin", runAlways = true)
    public void insertComments(MongoTemplate template) {
        template.save(new Comment("5edfdcce81099316f7254ca1", "Роман", book1));
        template.save(new Comment("5edfdcce81099316f7254ca2", "Сказка", book1));
    }
}
