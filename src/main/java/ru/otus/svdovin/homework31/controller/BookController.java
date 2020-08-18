package ru.otus.svdovin.homework31.controller;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.svdovin.homework31.domain.Book;
import ru.otus.svdovin.homework31.dto.ErrorEntity;
import ru.otus.svdovin.homework31.exception.APIException;
import ru.otus.svdovin.homework31.service.BookProvider;

import static ru.otus.svdovin.homework31.exception.ExceptionUtils.buildErrorData;

@RequiredArgsConstructor
@RestController
public class BookController {
    private Logger logger = LogManager.getLogger();
    
    private final BookProvider bookProvider;

    @GetMapping("/api/v1/book")
    public ResponseEntity<?> getBookAll(
    ) {
        try {
            return ResponseEntity.ok(bookProvider.getBookAll());
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

    @GetMapping("/api/v1/book/{id}")
    public ResponseEntity<?> getBookById(
            @PathVariable("id") long bookId
    ) {
        try {
            return ResponseEntity.ok(bookProvider.getBookById(bookId));
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

    @PutMapping("/api/v1/book")
    public ResponseEntity<?> updateBook(
            @RequestBody Book book
    ) {
        try {
            return ResponseEntity.ok(bookProvider.updateBook(book));
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

    @PostMapping("/api/v1/book")
    public ResponseEntity<?> createBook(
            @RequestBody Book book
    ) {
        try {
            return ResponseEntity.ok(bookProvider.createBook(book));
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

    @DeleteMapping("/api/v1/book/{id}")
    public ResponseEntity<?> deleteBookById(
            @PathVariable("id") long bookId
    ) {
        try {
            bookProvider.deleteBookById(bookId);
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
