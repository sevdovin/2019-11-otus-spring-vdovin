package ru.otus.svdovin.employmenthistory.service;

import ru.otus.svdovin.employmenthistory.dto.RecordTypeDto;

import java.util.List;

public interface RecordTypeProvider {
    RecordTypeDto getRecordType(long recordTypeId);
    List<RecordTypeDto> getRecordTypeAll();
    long createRecordType(RecordTypeDto recordTypeDto);
    void updateRecordType(RecordTypeDto recordTypeDto);
    void deleteRecordType(long recordTypeId);
}
