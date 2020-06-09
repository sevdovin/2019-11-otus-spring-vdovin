package ru.otus.svdovin.homework13.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comments")
public class Comment {

    @Id
    private String commentId;

    @Field("comment")
    private String comment;

    @DBRef
    private Book book;

    public String toString() {
        return String.format("Комментарий id=%s, книга=\"%s\", текст=\"%s\"", commentId, book, comment);
    }
}
