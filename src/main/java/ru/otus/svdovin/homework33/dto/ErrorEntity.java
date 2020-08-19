package ru.otus.svdovin.homework33.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorEntity {

    private ErrorData errorData = new ErrorData();

    private Integer status;

    public ErrorEntity() {}

    public ErrorEntity(Integer errorCode, String errorMessage) {
        errorData.errorCode = errorCode;
        errorData.errorMessage = errorMessage;
    }

    public static class ErrorData {
        private String errorMessage;
        private Integer errorCode;

        public String getErrorMessage() {
            return errorMessage;
        }

        public Integer getErrorCode() {
            return errorCode;
        }
    }

    public void setErrorData(ErrorData errorData) {
        this.errorData = errorData;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ErrorData getErrorData() {
        return errorData;
    }

    public Integer getStatus() {
        return status;
    }
}
