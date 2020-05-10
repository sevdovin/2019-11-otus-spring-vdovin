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
@ApiModel(value="AuthUser", description="Атрибуты пользователя")
public class AuthUserDto {
    @ApiModelProperty(notes = "Идентификатор записи", example = "1000")
    private Long userId;

    @ApiModelProperty(notes = "Идентификатор сотрудника", example = "1000")
    private Long employeeId;

    @ApiModelProperty(notes = "Логин", example = "user1")
    private String login;

    @ApiModelProperty(notes = "Хэш пароля", example = "12345678")
    private String password;

    @ApiModelProperty(notes = "Статус - активен/блокирован", example = "true")
    private Boolean isEnabled;

    @ApiModelProperty(notes = "Адрес электронной почты", example = "test@mail.ru")
    private String email;

    @ApiModelProperty(notes = "Идентификатор роли", example = "1000")
    private Long roleid;
}
