package ru.otus.svdovin.homework25.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class Book {

    @Id
    private long bookId;

    @Field("name")
    private String bookName;

    @DBRef
    private Genre genre;

    @DBRef
    private Author author;

    public String toString() {
        return String.format("Книга id=%s, наименование=\"%s\", автор=\"%s\", жанр=\"%s\"", bookId, bookName, author, genre);
    }
}
