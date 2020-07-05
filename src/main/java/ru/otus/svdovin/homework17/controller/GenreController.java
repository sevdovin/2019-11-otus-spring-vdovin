package ru.otus.svdovin.homework17.controller;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.svdovin.homework17.dto.ErrorEntity;
import ru.otus.svdovin.homework17.exception.APIException;
import ru.otus.svdovin.homework17.service.GenreProvider;

import static ru.otus.svdovin.homework17.exception.ExceptionUtils.buildErrorData;

@RequiredArgsConstructor
@RestController
public class GenreController {
    private Logger logger = LogManager.getLogger();
    
    private final GenreProvider genreProvider;

    @GetMapping("/api/v1/genre")
    public ResponseEntity<?> getGenreAll(
    ) {
        try {
            return ResponseEntity.ok(genreProvider.getGenreAll());
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
