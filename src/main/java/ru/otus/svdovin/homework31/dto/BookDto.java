package ru.otus.svdovin.homework31.dto;

import lombok.*;
import ru.otus.svdovin.homework31.domain.Book;
import ru.otus.svdovin.homework31.domain.Genre;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookDto {
    private long bookId;
    private String bookName;
    private Genre genre;
    private String authorsInfo;

    public static BookDto buildDTO(Book book) {
        BookDto result = BookDto.builder()
                .bookId(book.getBookId())
                .bookName(book.getBookName())
                .genre(book.getGenre())
                .authorsInfo(book.getAuthorsInfo())
                .build();
        return result;
    }
}
