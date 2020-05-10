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
@ApiModel(value="Employee", description="Атрибуты сотрудника")
public class EmployeeDto {
    @ApiModelProperty(notes = "Идентификатор сотрудника", example = "1000")
    private Long employeeId;

    @ApiModelProperty(notes = "Идентификатор предприятия", example = "1")
    private Long companyId;

    @ApiModelProperty(notes = "Фамилия", example = "Петрова")
    private String lastName;

    @ApiModelProperty(notes = "Имя", example = "Наталья")
    private String firstName;

    @ApiModelProperty(notes = "Отчество", example = "Олеговна")
    private String middleName;

    @ApiModelProperty(notes = "Дата рождения", example = "1980-12-08")
    private LocalDate birthday;

    @ApiModelProperty(notes = "СНИЛС", example = "078-123-765 26")
    private String snils;
}
