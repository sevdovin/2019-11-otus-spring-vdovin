package ru.otus.svdovin.employmenthistory.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
@NamedEntityGraph(name = "Employee.company", attributeNodes = @NamedAttributeNode("company"))
public class Employee {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "employee_id_seq")
    @SequenceGenerator(name = "employee_id_seq", sequenceName = "employee_id_seq", allocationSize = 1)
    private long employeeId;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "middlename")
    private String middleName;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "snils")
    private String snils;

    @ManyToOne
    @JoinColumn(name = "companyid", referencedColumnName = "id")
    private Company company;

    public Employee(long employeeId, String lastName, String firstName, String middleName, LocalDate birthday, String snils) {
        this.employeeId = employeeId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthday = birthday;
        this.snils = snils;
    }

    public String toString() {
        return String.format("Сотрудник id=%d, фамилия=\"%s\", имя=\"%s\", отчество=\"%s\", день рождения=\"%s\", СНИЛС=\"%s\"",
               employeeId, lastName, firstName, middleName, birthday, snils);
    }
}
