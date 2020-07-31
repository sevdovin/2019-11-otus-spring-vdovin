package ru.otus.svdovin.homework20.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class Book {

    @Id
    private String bookId;

    @Field("name")
    private String bookName;

    @DBRef
    private Genre genre;

    @DBRef
    private List<Author> authors;

    public String toString() {
        return String.format("Книга id=%s, наименование=\"%s\", авторы=\"%s\", жанр=\"%s\"", bookId, bookName, authors, genre);
    }

    public String getAuthorsInfo() {
        if (authors != null) {
            return authors.stream().map(Author::getAuthorName).collect(Collectors.joining(", "));
        } else {
            return "";
        }
    }
}
