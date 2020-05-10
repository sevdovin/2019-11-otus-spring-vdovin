package ru.otus.svdovin.employmenthistory.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.svdovin.employmenthistory.dto.AuthUserDto;
import ru.otus.svdovin.employmenthistory.dto.RecordDto;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "auth_user")
public class AuthUser {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "auth_user_id_seq")
    @SequenceGenerator(name = "auth_user_id_seq", sequenceName = "auth_user_id_seq", allocationSize = 1)
    private long userId;

    @Column(name = "employeeid")
    private Long employeeId;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "isenabled")
    private Boolean isEnabled;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "roleid", referencedColumnName = "id")
    private AuthRole authRole;

    public AuthUser(long userId, Long employeeId, String login, String password, Boolean isEnabled, String email) {
        this.userId = userId;
        this.employeeId = employeeId;
        this.login = login;
        this.password = password;
        this.isEnabled = isEnabled;
        this.email = email;
    }

    public String toString() {
        return String.format("Пользователь id=%d, сотрудник id=%d, логин=\"%s\", действует=%b, email=\"%s\"", 
               userId, employeeId, login, isEnabled, email);
    }

    public AuthUserDto buildDTO() {
        AuthUserDto result = AuthUserDto.builder()
                .userId(userId)
                .employeeId(employeeId)
                .login(login)
                .password(password)
                .isEnabled(isEnabled)
                .email(email)
                .build();
        if (authRole != null) {
            result.setRoleid(authRole.getRoleId());
        }
        return result;
    }
}
