package ru.otus.svdovin.homework09.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BOOKS")
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

    @ManyToMany(targetEntity = Author.class, fetch = FetchType.LAZY, cascade={CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
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
}
