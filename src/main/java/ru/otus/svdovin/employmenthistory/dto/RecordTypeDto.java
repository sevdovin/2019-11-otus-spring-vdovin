package ru.otus.svdovin.employmenthistory.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import ru.otus.svdovin.employmenthistory.domain.RecordType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value="RecordType", description="Атрибуты справочника типов записи трудовой книжки")
public class RecordTypeDto {
    @ApiModelProperty(notes = "Идентификатор записи справочника типов", example = "1000")
    private Long recordTypeId;

    @ApiModelProperty(notes = "Код типа", example = "1")
    private Integer typeCode;

    @ApiModelProperty(notes = "Наименование типа", example = "ПЕРЕВОД")
    private String typeName;

    public static RecordTypeDto buildDTO(RecordType recordType) {
        return RecordTypeDto.builder()
                .recordTypeId(recordType.getRecordTypeId())
                .typeCode(recordType.getTypeCode())
                .typeName(recordType.getTypeName())
                .build();
    }
}
