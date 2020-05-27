package ru.otus.svdovin.employmenthistory.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import ru.otus.svdovin.employmenthistory.domain.AuthUser;

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

    @ApiModelProperty(notes = "Системное наименование роли", example = "admin")
    private String roleSysName;

    @ApiModelProperty(notes = "ФИО сотрудника", example = "Иванов Иван Иванович")
    private String empFio;

    public static AuthUserDto buildDTO(AuthUser authUser) {
        AuthUserDto result = AuthUserDto.builder()
                .userId(authUser.getUserId())
                .login(authUser.getLogin())
                .password(authUser.getPassword())
                .isEnabled(authUser.getIsEnabled())
                .email(authUser.getEmail())
                .build();
        if (authUser.getEmployee() != null) {
            String lastName = authUser.getEmployee().getLastName();
            String firstName = authUser.getEmployee().getFirstName();
            String middleName = authUser.getEmployee().getMiddleName();
            result.setEmployeeId(authUser.getEmployee().getEmployeeId());
            result.setEmpFio(lastName + " " + firstName + " " + middleName);
        } else {
            result.setEmployeeId(0L);
            result.setEmpFio("");
        }
        if (authUser.getAuthRole() != null) {
            result.setRoleid(authUser.getAuthRole().getRoleId());
            result.setRoleSysName(authUser.getAuthRole().getRoleSysName());
        } else {
            result.setRoleid(0L);
            result.setRoleSysName("");
        }
        return result;
    }
}
