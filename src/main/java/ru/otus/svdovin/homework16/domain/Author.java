package ru.otus.svdovin.homework16.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
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

    @EqualsAndHashCode.Exclude
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
