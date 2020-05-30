package ru.otus.svdovin.employmenthistory.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.svdovin.employmenthistory.dto.AuthUserDto;
import ru.otus.svdovin.employmenthistory.dto.ErrorEntity;
import ru.otus.svdovin.employmenthistory.exception.APIException;
import ru.otus.svdovin.employmenthistory.service.AuthUserProvider;

import javax.validation.Valid;

import static ru.otus.svdovin.employmenthistory.exception.ExceptionUtils.buildErrorData;

@RequiredArgsConstructor
@RestController
@Api(description = "REST API для пользователей", tags = { "AuthUser / Пользователи" })
public class AuthUserController {
    private Logger logger = LogManager.getLogger();
    
    private final AuthUserProvider authUserProvider;

    @GetMapping("/authuser/{id}")
    @ApiOperation(value = "Получение пользователя по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AuthUserDto.class),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> getAuthUserById(
            @ApiParam(name = "id", value = "Идентификатор записи таблицы", required = true)
            @PathVariable("id") long authUserId
    ) {
        try {
            return ResponseEntity.ok(authUserProvider.getAuthUser(authUserId));
        } catch (APIException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(buildErrorData(e));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorEntity(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            String.format("Internal exception, please see server log, message: '%s'", e.getLocalizedMessage())
                    )
            );
        }
    }

    @GetMapping("/api/v1/authuser")
    @ApiOperation(value = "Получение всех пользователей")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AuthUserDto.class, responseContainer = "List"),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> getEmployeeAll(
    ) {
        try {
            return ResponseEntity.ok(authUserProvider.getAuthUserAll());
        } catch (APIException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(buildErrorData(e));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorEntity(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            String.format("Internal exception, please see server log, message: '%s'", e.getLocalizedMessage())
                    )
            );
        }
    }

    @PostMapping("/api/v1/authuser")
    @ApiOperation(value = "Создание нового пользователя", notes = "Роли: personnelofficer")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Long.class),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> createAuthUser(
            @Valid
            @RequestBody
            @ApiParam(name = "authUser", value = "Данные пользователя", required=true)
                    AuthUserDto authUserDto
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(authUserProvider.createAuthUser(authUserDto));
        } catch (APIException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(buildErrorData(e));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorEntity(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            String.format("Internal exception, please see server log, message: '%s'", e.getMessage())
                    )
            );
        }
    }

    @PutMapping("/api/v1/authuser")
    @ApiOperation(value = "Изменение пользователя", notes = "Роли: personnelofficer")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> updateAuthUser(
            @Valid
            @RequestBody
            @ApiParam(name = "updateAuthUser", value = "Данные пользователя", required=true)
                    AuthUserDto authUserDto
    ) {
        try {
            authUserProvider.updateAuthUser(authUserDto);
            return ResponseEntity.ok().build();
        } catch (APIException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(buildErrorData(e));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorEntity(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            String.format("Internal exception, please see server log, message: '%s'", e.getMessage())
                    )
            );
        }
    }

    @DeleteMapping("/api/v1/authuser/{authUserId}")
    @ApiOperation(value = "Удаление пользователя", notes = "Роли: personnelofficer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> removeAuthUser(
            @Valid
            @ApiParam(name = "authUserId", value = "Идентификатор записи таблицы", required=true)
            @PathVariable("authUserId")
                    long authUserId
    ) {
        try {
            authUserProvider.deleteAuthUser(authUserId);
            return ResponseEntity.noContent().build();
        } catch (APIException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(buildErrorData(e));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorEntity(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            String.format("Internal exception, please see server log, message: '%s'", e.getLocalizedMessage())
                    )
            );
        }
    }

    @PostMapping("/api/v1/authuser/enabled")
    @ApiOperation(value = "Создание нового пользователя", notes = "Роли: personnelofficer")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Long.class),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> changeEnabledAuthUser(
            @Valid
            @ApiParam(name = "authUserId", value = "Идентификатор записи таблицы", required=true)
            @PathVariable("authUserId")
                    long authUserId,
            @Valid
            @ApiParam(name = "isEnabled", value = "Статус - активен/блокирован", required=true)
            @PathVariable("isEnabled")
                    boolean isEnabled
    ) {
        try {
            authUserProvider.changeEnabledAuthUser(authUserId, isEnabled);
            authUserProvider.deleteAuthUser(authUserId);
            return ResponseEntity.noContent().build();
        } catch (APIException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(buildErrorData(e));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorEntity(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            String.format("Internal exception, please see server log, message: '%s'", e.getMessage())
                    )
            );
        }
    }
}
