package ru.otus.svdovin.employmenthistory.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
}
