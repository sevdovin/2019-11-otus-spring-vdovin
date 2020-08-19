package ru.otus.svdovin.homework33.domain;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BOOKS")
@NamedEntityGraph(name = "BookWithAuthorAndGenre",
        attributeNodes = {@NamedAttributeNode(value = "authors"),
                @NamedAttributeNode(value = "genre")})
public class Book {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;

    @Column(name = "NAME")
    private String bookName;

    @ManyToOne
    @JoinColumn(name = "GENREID", referencedColumnName = "ID")
    private Genre genre;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(targetEntity = Author.class, fetch = FetchType.LAZY, cascade={CascadeType.REFRESH, CascadeType.MERGE/*, CascadeType.PERSIST*/})
    @JoinTable(name = "BOOKS_AUTHORS",
            joinColumns = @JoinColumn(name = "BOOKID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHORID", referencedColumnName = "ID"))
    private List<Author> authors;

    public Book(long bookId, String bookName, Genre genre) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.genre = genre;
    }

    public String toString() {
        return String.format("Книга id=%d, наименование=\"%s\", авторы=\"%s\", жанр=\"%s\"", bookId, bookName, authors, genre);
    }

    public String getAuthorsInfo() {
        if (authors != null) {
            return authors.stream().map(Author::getAuthorName).collect(Collectors.joining(", "));
        } else {
            return "";
        }
    }
}
