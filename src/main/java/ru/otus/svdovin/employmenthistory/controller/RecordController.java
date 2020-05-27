package ru.otus.svdovin.employmenthistory.controller;

import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.svdovin.employmenthistory.dto.ErrorEntity;
import ru.otus.svdovin.employmenthistory.dto.RecordDto;
import ru.otus.svdovin.employmenthistory.exception.APIException;
import ru.otus.svdovin.employmenthistory.service.RecordProvider;

import javax.validation.Valid;

import static ru.otus.svdovin.employmenthistory.exception.ExceptionUtils.buildErrorData;

@RestController
@Api(description = "REST API для записей трудовой книжки", tags = { "Record / Записи трудовой книжки" })
public class RecordController {
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private RecordProvider recordProvider;

    @GetMapping("/api/v1/record/{id}")
    @ApiOperation(value = "Получение записи трудовой книжки по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RecordDto.class),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> getRecordById(
            @ApiParam(name = "id", value = "Идентификатор записи трудовой книжки", required = true)
            @PathVariable("id") long recordId
    ) {
        try {
            return ResponseEntity.ok(recordProvider.getRecord(recordId));
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

    @GetMapping("/api/v1/record/employee/{id}")
    @ApiOperation(value = "Получение записей трудовой книжки по идентификатору сотрудника")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RecordDto.class),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> getRecordByEmployeeId(
            @ApiParam(name = "id", value = "Идентификатор сотрудника", required = true)
            @PathVariable("id") long employeeId
    ) {
        try {
            return ResponseEntity.ok(recordProvider.getRecordsByEmployeeId(employeeId));
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

    @PostMapping("/api/v1/record")
    @ApiOperation(value = "Создание записи трудовой книжки", notes = "Роли: personnelofficer")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Long.class),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> createRecord(
            @Valid
            @RequestBody
            @ApiParam(name = "record", value = "Данные записи трудовой книжки", required=true)
                    RecordDto recordDto
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(recordProvider.createRecord(recordDto));
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

    @PutMapping("/api/v1/record")
    @ApiOperation(value = "Изменение записи трудовой книжки", notes = "Роли: personnelofficer")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> updateRecord(
            @Valid
            @RequestBody
            @ApiParam(name = "updateRecord", value = "Данные записи трудовой книжки", required=true)
                    RecordDto recordDto
    ) {
        try {
            recordProvider.updateRecord(recordDto);
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

    @DeleteMapping("/api/v1/record/{recordId}")
    @ApiOperation(value = "Удаление записи трудовой книжки", notes = "Роли: personnelofficer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> removeRecord(
            @Valid
            @ApiParam(name = "recordId", value = "Идентификатор записи трудовой книжки", required=true)
            @PathVariable("recordId")
                    long recordId
    ) {
        try {
            recordProvider.deleteRecord(recordId);
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
