package ru.otus.svdovin.employmenthistory.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recordtype")
public class RecordType {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "recordtype_id_seq")
    @SequenceGenerator(name = "recordtype_id_seq", sequenceName = "recordtype_id_seq", allocationSize = 1)
    private long recordTypeId;

    @Column(name = "typecode")
    private Integer typeCode;

    @Column(name = "name")
    private String typeName;
}
