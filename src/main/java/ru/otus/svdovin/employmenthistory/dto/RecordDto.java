package ru.otus.svdovin.employmenthistory.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import ru.otus.svdovin.employmenthistory.domain.Record;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value="Record", description="Атрибуты записи трудовой книдки")
public class RecordDto {
    @ApiModelProperty(notes = "Идентификатор записи трудовой книдки", example = "1000")
    private Long recordId;

    @ApiModelProperty(notes = "Дата приема, перевода, увольнения", example = "2020-12-08")
    private LocalDate date;

    @ApiModelProperty(notes = "Трудовая функция", example = "Бухгалтер")
    private String position;

    @ApiModelProperty(notes = "Код выполняемой функции", example = "")
    private String code;

    @ApiModelProperty(notes = "Причины увольнения", example = "ФЗ-244 п.4")
    private String reason;

    @ApiModelProperty(notes = "Основание: наименование документа", example = "Приказ")
    private String docName;

    @ApiModelProperty(notes = "Основание: дата документа", example = "2020-12-08")
    private LocalDate docDate;

    @ApiModelProperty(notes = "Основание: номер документа", example = "123")
    private String docNumber;

    @ApiModelProperty(notes = "Признак отмены записи", example = "")
    private String cancel;

    @ApiModelProperty(notes = "Идентификатор сотрудника", example = "1")
    private Long employeeId;

    @ApiModelProperty(notes = "Идентификатор типа записи", example = "1")
    private Long typeCodeId;

    @ApiModelProperty(notes = "Код типа", example = "1")
    private Integer typeCode;

    @ApiModelProperty(notes = "Наименование типа", example = "ПЕРЕВОД")
    private String typeName;

    public static RecordDto buildDTO(Record record) {
        RecordDto result = RecordDto.builder()
                .recordId(record.getRecordId())
                .date(record.getDate())
                .position(record.getPosition())
                .code(record.getCode())
                .reason(record.getReason())
                .docName(record.getDocName())
                .docDate(record.getDocDate())
                .docNumber(record.getDocNumber())
                .cancel(record.getCancel())
                .build();
        if (record.getEmployee() != null) {
            result.setEmployeeId(record.getEmployee().getEmployeeId());
        }
        if (record.getRecordType() != null) {
            result.setTypeCodeId(record.getRecordType().getRecordTypeId());
            result.setTypeCode(record.getRecordType().getTypeCode());
            result.setTypeName(record.getRecordType().getTypeName());
        }
        return result;
    }
}
