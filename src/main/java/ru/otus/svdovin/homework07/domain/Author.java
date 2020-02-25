package ru.otus.svdovin.homework07.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Author {

    private long authorId;
    private String authorName;
}
