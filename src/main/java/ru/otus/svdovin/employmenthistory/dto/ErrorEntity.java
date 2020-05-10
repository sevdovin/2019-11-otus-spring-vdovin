package ru.otus.svdovin.employmenthistory.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorEntity {

    @ApiModelProperty(notes = "Информация об ошибке", example = "{\n" +
            "    \"errorMessage\": \"Локализованное сообщение об ошибке\",\n" +
            "    \"errorCode\": 1\n" +
            "  }")
    private ErrorData errorData = new ErrorData();

    @ApiModelProperty(notes = "Статус", example = "422")
    private Integer status;

    public ErrorEntity() {}

    public ErrorEntity(Integer errorCode, String errorMessage) {
        errorData.errorCode = errorCode;
        errorData.errorMessage = errorMessage;
    }

    public static class ErrorData {
        @ApiModelProperty(notes = "Локализованное сообщение об ошибке", example = "Локализованное сообщение об ошибке")
        private String errorMessage;

        @ApiModelProperty(notes = "Код ошибки", example = "1")
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
