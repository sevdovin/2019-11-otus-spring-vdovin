package ru.otus.svdovin.employmenthistory.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.svdovin.employmenthistory.dto.CompanyDto;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "company")
public class Company {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "company_id_seq")
    @SequenceGenerator(name = "company_id_seq", sequenceName = "company_id_seq", allocationSize = 1)
    private long companyId;

    @Column(name = "name")
    private String companyName;

    @Column(name = "inn")
    private String companyInn;

    @Column(name = "kpp")
    private String companyKpp;

    @Column(name = "pfr")
    private String companyPfr;

    @Column(name = "position")
    private String chiefPosition;

    @Column(name = "fio")
    private String chiefFio;

    public CompanyDto buildDTO() {
        return CompanyDto.builder()
                .companyId(companyId)
                .companyName(companyName)
                .companyInn(companyInn)
                .companyKpp(companyKpp)
                .companyPfr(companyPfr)
                .chiefPosition(chiefPosition)
                .chiefFio(chiefFio)
                .build();
    }

}
