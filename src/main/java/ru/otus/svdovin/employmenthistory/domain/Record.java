package ru.otus.svdovin.employmenthistory.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.svdovin.employmenthistory.dto.RecordDto;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "record")
public class Record {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "record_id_seq")
    @SequenceGenerator(name = "record_id_seq", sequenceName = "record_id_seq", allocationSize = 1)
    private long recordId;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "position")
    private String position;

    @Column(name = "code")
    private String code;

    @Column(name = "reason")
    private String reason;

    @Column(name = "docname")
    private String docName;

    @Column(name = "docdate")
    private LocalDate docDate;

    @Column(name = "docnumber")
    private String docNumber;

    @Column(name = "cancel")
    private String cancel;

    @ManyToOne
    @JoinColumn(name = "employeeid", referencedColumnName = "id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "typecodeid", referencedColumnName = "id")
    private RecordType recordType;

    public Record(long recordId, LocalDate date, String position, String code, String reason, String docName, LocalDate docDate, String docNumber, String cancel) {
        this.recordId = recordId;
        this.date = date;
        this.position = position;
        this.code = code;
        this.reason = reason;
        this.docName = docName;
        this.docDate = docDate;
        this.docNumber = docNumber;
        this.cancel = cancel;
    }

    public String toString() {
        return String.format("Запись книжки id=%d, дата=%s, трудовая функция=\"%s\", код функции=\"%s\", причины увольнения==\"%s\"" +
               " , основание: наименование документа=\"%s\", основание: дата документа=%s, основание: номер документа=\"%s\", признак отмены записи=\"%s\"",
               recordId, date, position, code, reason, docName, docDate, docNumber, cancel);
    }

    public RecordDto buildDTO() {
        RecordDto result = RecordDto.builder()
                .recordId(recordId)
                .date(date)
                .position(position)
                .code(code)
                .reason(reason)
                .docName(docName)
                .docDate(docDate)
                .docNumber(docNumber)
                .cancel(cancel)
                .build();
        if (employee != null) {
            result.setEmployeeId(employee.getEmployeeId());
        }
        if (recordType != null) {
            result.setTypeCodeId(recordType.getRecordTypeId());
        }
        return result;
    }
}
