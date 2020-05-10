package ru.otus.svdovin.employmenthistory.exception;

import ru.otus.svdovin.employmenthistory.dto.ErrorEntity;

public class ExceptionUtils {

    public static ErrorEntity buildErrorData(Integer status, String errorMessage, Integer errorCode) {
        ErrorEntity error = new ErrorEntity(errorCode, errorMessage);
        error.setStatus(status);
        return error;
    }

    public static ErrorEntity buildErrorData(APIException apiException) {
        return buildError(apiException);
    }

    public static ErrorEntity buildError(APIException apiException) {
        ErrorEntity error = new ErrorEntity(apiException.getCode(), apiException.getMessage());
        error.setStatus(apiException.getStatus());
        return error;
    }
}
