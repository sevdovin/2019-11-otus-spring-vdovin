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

    @OneToOne
    @JoinColumn(name = "AUTHORID")
    private Author author;

    @OneToOne
    @JoinColumn(name = "GENREID")
    private Genre genre;

    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOKID")
    private List<Comment> comments;

    public Book(long bookId, String bookName, Author author, Genre genre) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.genre = genre;
    }

    public String toString() {
        return String.format("Книга id=%d, наименование=\"%s\", автор=\"%s\", жанр=\"%s\"", bookId, bookName, author, genre);
    }

}
