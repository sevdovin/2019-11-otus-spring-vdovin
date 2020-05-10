package ru.otus.svdovin.employmenthistory.controller;

import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.svdovin.employmenthistory.dto.AuthRoleDto;
import ru.otus.svdovin.employmenthistory.dto.ErrorEntity;
import ru.otus.svdovin.employmenthistory.exception.APIException;
import ru.otus.svdovin.employmenthistory.service.AuthRoleProvider;

import javax.validation.Valid;

import static ru.otus.svdovin.employmenthistory.exception.ExceptionUtils.buildErrorData;

@RestController("AuthRoleController_v1")
@RequestMapping("/authrole/v1")
@Api(description = "REST API для ролей пользователей", tags = { "AuthRole / Роли пользователей" })
public class AuthRoleController {
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    AuthRoleProvider authRoleProvider;

    @GetMapping("/{id}")
    @ApiOperation(value = "Получение ролей пользователей по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AuthRoleDto.class),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> getAuthRoleById(
            @ApiParam(name = "id", value = "Идентификатор записи таблицы", required = true)
            @PathVariable("id") long authRoleId
    ) {
        try {
            return ResponseEntity.ok(authRoleProvider.getAuthRole(authRoleId));
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

    @GetMapping("/all")
    @ApiOperation(value = "Получение всех ролей пользователей")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AuthRoleDto.class, responseContainer = "List"),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> getEmployeeAll(
    ) {
        try {
            return ResponseEntity.ok(authRoleProvider.getAuthRoleAll());
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

    @PostMapping
    @ApiOperation(value = "Создание новой роли пользователей", notes = "Роли: personnelofficer")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Long.class),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> createAuthRole(
            @Valid
            @RequestBody
            @ApiParam(name = "authRole", value = "Данные роли пользователей", required=true)
                    AuthRoleDto authRoleDto
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(authRoleProvider.createAuthRole(authRoleDto));
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

    @PutMapping
    @ApiOperation(value = "Изменение роли пользователей", notes = "Роли: personnelofficer")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> updateAuthRole(
            @Valid
            @RequestBody
            @ApiParam(name = "updateAuthRole", value = "Данные роли пользователей", required=true)
                    AuthRoleDto authRoleDto
    ) {
        try {
            authRoleProvider.updateAuthRole(authRoleDto);
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

    @DeleteMapping("/{authRoleId}")
    @ApiOperation(value = "Удаление ролей пользователей", notes = "Роли: personnelofficer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> removeAuthRole(
            @Valid
            @ApiParam(name = "authRoleId", value = "Идентификатор записи таблицы", required=true)
            @PathVariable("authRoleId")
                    long authRoleId
    ) {
        try {
            authRoleProvider.deleteAuthRole(authRoleId);
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
}
