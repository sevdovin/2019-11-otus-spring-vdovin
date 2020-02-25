package ru.otus.svdovin.homework07.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Genre {

    private long genreId;
    private String genreName;
}
