package ru.otus.svdovin.homework25.domain.mongo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "authors")
public class Author {

    @Id
    private long authorId;

    @Field("name")
    private String authorName;

    public String toString() {
        return String.format("Автор id=%s, наименование=\"%s\"", authorId, authorName);
    }
}
