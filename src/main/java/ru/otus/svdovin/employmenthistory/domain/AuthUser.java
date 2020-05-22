package ru.otus.svdovin.employmenthistory.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "auth_user")
@NamedEntityGraph(name = "AuthUser.authRole.employee", attributeNodes = {@NamedAttributeNode("authRole"), @NamedAttributeNode("employee")})
public class AuthUser {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "auth_user_id_seq")
    @SequenceGenerator(name = "auth_user_id_seq", sequenceName = "auth_user_id_seq", allocationSize = 1)
    private long userId;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "isenabled")
    private Boolean isEnabled;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "employeeid", referencedColumnName = "id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "roleid", referencedColumnName = "id")
    private AuthRole authRole;

    public AuthUser(long userId, String login, String password, Boolean isEnabled, String email) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.isEnabled = isEnabled;
        this.email = email;
    }

    public String toString() {
        return String.format("Пользователь id=%d, логин=\"%s\", действует=%b, email=\"%s\"",
               userId, login, isEnabled, email);
    }
}
