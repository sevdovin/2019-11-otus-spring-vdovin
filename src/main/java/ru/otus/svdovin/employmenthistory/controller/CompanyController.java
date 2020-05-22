package ru.otus.svdovin.employmenthistory.controller;

import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.svdovin.employmenthistory.dto.CompanyDto;
import ru.otus.svdovin.employmenthistory.dto.ErrorEntity;
import ru.otus.svdovin.employmenthistory.exception.APIException;
import ru.otus.svdovin.employmenthistory.service.CompanyProvider;

import javax.validation.Valid;

import static ru.otus.svdovin.employmenthistory.exception.ExceptionUtils.buildErrorData;

@RestController
@Api(description = "REST API для предприятия", tags = { "Company / Предприятие" })
public class CompanyController {
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private CompanyProvider companyProvider;

    @GetMapping("/company/{id}")
    @ApiOperation(value = "Получение предприятия по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CompanyDto.class),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> getCompanyById(
            @ApiParam(name = "id", value = "Идентификатор предприятия", required = true)
            @PathVariable("id") long companyId
    ) {
        try {
            return ResponseEntity.ok(companyProvider.getCompany(companyId));
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

    @PutMapping("/company")
    @ApiOperation(value = "Изменение атрибутов предприятия", notes = "Роли: personnelofficer")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 422, message = "API Exception", response = ErrorEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<?> updateCompany(
            @Valid
            @RequestBody
            @ApiParam(name = "updateCompany", value = "Данные атрибутов предприятия", required=true)
                    CompanyDto companyDto
    ) {
        try {
            companyProvider.updateCompany(companyDto);
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
}
