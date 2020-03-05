package ru.otus.svdovin.homework09.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "COMMENTS")
public class Comment {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @Column(name = "COMMENT", length = 4000)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "BOOKID", referencedColumnName = "ID")
    private Book book;

    public Comment(long commentId, String comment) {
        this.commentId = commentId;
        this.comment = comment;
    }
}
