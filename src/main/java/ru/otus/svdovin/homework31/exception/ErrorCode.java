package ru.otus.svdovin.homework31.exception;

public enum  ErrorCode {
    INVALID_COMPANY_ID(10),
    COMPANY_NOT_FOUND(11),
    INVALID_EMPLOYEE_ID(12),
    EMPLOYEE_NOT_FOUND(13),
    EMPLOYEE_USE_IN_RECORD(14),
    INVALID_RECORD_ID(15),
    INVALID_TYPECODE_ID(16),
    RECORDTYPE_NOT_FOUND(17),
    RECORD_NOT_FOUND(18),
    INVALID_RECORDTYPE_ID(19),
    RECORDTYPE_USE_IN_RECORD(20),
    INVALID_AUTHROLE_ID(21),
    AUTHROLE_NOT_FOUND(22),
    AUTHUSER_NOT_FOUND(24),
    INVALID_AUTHUSER_ID(25);

    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
