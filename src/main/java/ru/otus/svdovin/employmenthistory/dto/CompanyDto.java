package ru.otus.svdovin.employmenthistory.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value="Company", description="Атрибуты предприятия")
public class CompanyDto {
    @ApiModelProperty(notes = "Идентификатор предприятия", example = "1")
    private Long companyId;

    @ApiModelProperty(notes = "Наименование предприятия", example = "ООО Рога и копыта")
    private String companyName;

    @ApiModelProperty(notes = "ИНН предприятия", example = "7743098123")
    private String companyInn;

    @ApiModelProperty(notes = "КПП предприятия", example = "774301001")
    private String companyKpp;

    @ApiModelProperty(notes = "Регистрационный номер в ПФР", example = "087-109-654321")
    private String companyPfr;

    @ApiModelProperty(notes = "Наименование должности руководителя", example = "Директор")
    private String chiefPosition;

    @ApiModelProperty(notes = "ФИО руководителя", example = "Иванов И.И.")
    private String chiefFio;
}
