package ru.otus.svdovin.homework20.dto;

import lombok.*;
import ru.otus.svdovin.homework20.domain.Book;
import ru.otus.svdovin.homework20.domain.Genre;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookDto {
    private String bookId;
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
