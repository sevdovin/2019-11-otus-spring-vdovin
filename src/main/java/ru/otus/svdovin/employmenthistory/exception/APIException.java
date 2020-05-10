package ru.otus.svdovin.employmenthistory.exception;

import org.springframework.http.HttpStatus;

/**
 * Класс для инкапсуляции бизнес-исключений API. Данные исключения не являются
 * ошибкой клиента, вызывающего сервис, они используются для сообщения о, в
 * принципе, ожидаемых, но непреодолимых в данный момент ситуациях, которые
 * клиенту стоит каким-то образом пытаться обработать.
 *
 * @author ashafranov
 */
public class APIException extends RuntimeException {

    private static final long serialVersionUID = 2068181330829155946L;

    private final int status;

    private int code;

    private String message;

    public APIException(int status, String message) {
        super(message);
        this.message = message;
        this.status = status;
    }

    public APIException(int status, String message, int code) {
        this(status, message);
        this.code = code;
    }

    public APIException(String message, int code) {
        super(message);
        this.message = message;
        this.status = HttpStatus.UNPROCESSABLE_ENTITY.value();
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() { return message;}
}
