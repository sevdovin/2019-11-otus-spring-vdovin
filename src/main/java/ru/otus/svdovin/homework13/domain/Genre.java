package ru.otus.svdovin.homework13.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "genres")
public class Genre {

    @Id
    private String genreId;

    @Field("name")
    private String genreName;

    public String toString() {
        return String.format("Жанр id=%s, наименование=\"%s\"", genreId, genreName);
    }
}
