package ru.otus.svdovin.homework11.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "GENRES")
public class Genre {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long genreId;

    @Column(name = "NAME")
    private String genreName;

    public String toString() {
        return String.format("Жанр id=%d, наименование=\"%s\"", genreId, genreName);
    }
}
