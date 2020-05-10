package ru.otus.svdovin.employmenthistory.service;

import ru.otus.svdovin.employmenthistory.dto.RecordDto;

import java.util.List;

public interface RecordProvider {
    RecordDto getRecord(long recordId);
    List<RecordDto> getRecordsByEmployeeId(long employeeId);
    long createRecord(RecordDto recordDto);
    void updateRecord(RecordDto recordDto);
    void deleteRecord(long recordId);
}
