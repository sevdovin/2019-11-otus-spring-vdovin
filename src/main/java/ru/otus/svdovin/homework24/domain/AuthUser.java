package ru.otus.svdovin.homework24.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AUTH_USER")
public class AuthUser {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @ManyToOne
    @JoinColumn(name = "roleid", referencedColumnName = "id")
    private AuthRole authRole;

    public AuthUser(long userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public String toString() {
        return String.format("Пользователь id=%d, логин=\"%s\"",
               userId, username);
    }
}
