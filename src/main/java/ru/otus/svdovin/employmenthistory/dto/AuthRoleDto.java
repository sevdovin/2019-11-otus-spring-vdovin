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
@ApiModel(value=" AuthRole", description="Атрибуты справочника ролей")
public class AuthRoleDto {
    @ApiModelProperty(notes = "Идентификатор записи", example = "1000")
    private Long roleId;

    @ApiModelProperty(notes = "Системное имя роли", example = "admin")
    private String roleSysName;

    @ApiModelProperty(notes = "Наименование роли", example = "Администратор")
    private String roleName;
}
