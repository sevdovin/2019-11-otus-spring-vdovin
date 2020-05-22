package ru.otus.svdovin.employmenthistory.controller;

import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.svdovin.employmenthistory.dto.RecordTypeDto;
import ru.otus.svdovin.employmenthistory.dto.ErrorEntity;
import ru.otus.svdovin.employmenthistory.exception.APIException;
import ru.otus.svdovin.employmenthistory.service.RecordTypeProvider;

import javax.validation.Valid;

import static ru.otus.svdovin.employmenthistory.exception.ExceptionUtils.buildErrorData;

@RestController
@Api(description = "REST API для типов записей в трудовую книжку", tags = { "RecordType / Типы записей" })
public class RecordTypeController {
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private RecordTypeProvider recordTypeProvider;

    @GetMapping("/recordtype/{id}")
    @ApiOperation(value = "Получение типа записи по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RecordTypeDto.class),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> getRecordTypeById(
            @ApiParam(name = "id", value = "Идентификатор записи таблицы", required = true)
            @PathVariable("id") long recordTypeId
    ) {
        try {
            return ResponseEntity.ok(recordTypeProvider.getRecordType(recordTypeId));
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

    @GetMapping("/recordtype")
    @ApiOperation(value = "Получение всех типов записи в трудовую книжку")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RecordTypeDto.class, responseContainer = "List"),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> getEmployeeAll(
    ) {
        try {
            return ResponseEntity.ok(recordTypeProvider.getRecordTypeAll());
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

    @PostMapping("/recordtype")
    @ApiOperation(value = "Создание нового типа записи в трудовую книжку", notes = "Роли: personnelofficer")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Long.class),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> createRecordType(
            @Valid
            @RequestBody
            @ApiParam(name = "recordType", value = "Данные типа записи", required=true)
                    RecordTypeDto recordTypeDto
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(recordTypeProvider.createRecordType(recordTypeDto));
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

    @PutMapping("/recordtype")
    @ApiOperation(value = "Изменение типа записи в трудовую книжку", notes = "Роли: personnelofficer")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> updateRecordType(
            @Valid
            @RequestBody
            @ApiParam(name = "updateRecordType", value = "Данные типа записи", required=true)
                    RecordTypeDto recordTypeDto
    ) {
        try {
            recordTypeProvider.updateRecordType(recordTypeDto);
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

    @DeleteMapping("/recordtype/{recordTypeId}")
    @ApiOperation(value = "Удаление типа записи в трудовую книжку", notes = "Роли: personnelofficer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> removeRecordType(
            @Valid
            @ApiParam(name = "recordTypeId", value = "Идентификатор записи таблицы", required=true)
            @PathVariable("recordTypeId")
                    long recordTypeId
    ) {
        try {
            recordTypeProvider.deleteRecordType(recordTypeId);
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
