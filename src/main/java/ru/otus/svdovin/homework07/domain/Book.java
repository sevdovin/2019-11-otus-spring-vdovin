package ru.otus.svdovin.homework07.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {

    private long bookId;
    private String bookName;
    private Author author;
    private Genre genre;
}
