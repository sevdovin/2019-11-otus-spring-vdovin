package ru.otus.svdovin.employmenthistory.controller;

import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.svdovin.employmenthistory.dto.EmployeeDto;
import ru.otus.svdovin.employmenthistory.dto.ErrorEntity;
import ru.otus.svdovin.employmenthistory.exception.APIException;
import ru.otus.svdovin.employmenthistory.service.EmployeeProvider;

import javax.validation.Valid;

import java.util.List;

import static ru.otus.svdovin.employmenthistory.exception.ExceptionUtils.buildErrorData;

@RestController
@Api(description = "REST API для сотрудников", tags = { "Employee / Сотрудники" })
public class EmployeeController {
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private EmployeeProvider employeeProvider;

    @GetMapping("/api/v1/employee/{id}")
    @ApiOperation(value = "Получение сотрудника по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = EmployeeDto.class),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> getEmployeeById(
            @ApiParam(name = "id", value = "Идентификатор сотрудника", required = true)
            @PathVariable("id") long employeeId
    ) {
        try {
            return ResponseEntity.ok(employeeProvider.getEmployee(employeeId));
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

    @GetMapping("/api/v1/employee")
    @ApiOperation(value = "Получение всех сотрудников")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = EmployeeDto.class, responseContainer = "List"),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> getEmployeeAll(
    ) {
        try {
            return ResponseEntity.ok(employeeProvider.getEmployeeAll());
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

    @PostMapping("/api/v1/employee")
    @ApiOperation(value = "Создание нового сотрудника", notes = "Роли: personnelofficer")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Long.class),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> createEmployee(
            @Valid
            @RequestBody
            @ApiParam(name = "employee", value = "Данные атрибутов сотрудника", required=true)
                    EmployeeDto employeeDto
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(employeeProvider.createEmployee(employeeDto));
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

    @PutMapping("/api/v1/employee")
    @ApiOperation(value = "Изменение атрибутов сотрудника", notes = "Роли: personnelofficer")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> updateEmployee(
            @Valid
            @RequestBody
            @ApiParam(name = "updateEmployee", value = "Данные атрибутов сотрудника", required=true)
                    EmployeeDto employeeDto
    ) {
        try {
            employeeProvider.updateEmployee(employeeDto);
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

    @DeleteMapping("/api/v1/employee/{employeeId}")
    @ApiOperation(value = "Удаление сотрудника", notes = "Роли: personnelofficer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> removeEmployee(
            @Valid
            @ApiParam(name = "employeeId", value = "Идентификатор сотрудника", required=true)
            @PathVariable("employeeId")
                    long employeeId
    ) {
        try {
            employeeProvider.deleteEmployee(employeeId);
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
