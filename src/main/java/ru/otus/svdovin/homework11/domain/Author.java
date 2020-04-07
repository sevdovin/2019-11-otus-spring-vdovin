package ru.otus.svdovin.homework11.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AUTHORS")
public class Author {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long authorId;

    @Column(name = "NAME")
    private String authorName;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;

    public Author(long authorId, String authorName) {
        this.authorId = authorId;
        this.authorName = authorName;
    }

    public String toString() {
        return String.format("Автор id=%d, наименование=\"%s\"", authorId, authorName);
    }
}
