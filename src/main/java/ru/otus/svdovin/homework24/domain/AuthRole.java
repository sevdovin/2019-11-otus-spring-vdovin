package ru.otus.svdovin.homework24.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AUTH_ROLE")
public class AuthRole {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;

    @Column(name = "SYSNAME")
    private String roleSysName;

    @Column(name = "NAME")
    private String roleName;
}
