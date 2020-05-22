package ru.otus.svdovin.employmenthistory.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "auth_role")
public class AuthRole {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "auth_role_id_seq")
    @SequenceGenerator(name = "auth_role_id_seq", sequenceName = "auth_role_id_seq", allocationSize = 1)
    private long roleId;

    @Column(name = "sysname")
    private String roleSysName;

    @Column(name = "name")
    private String roleName;
}
