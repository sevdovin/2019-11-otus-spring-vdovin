package ru.otus.svdovin.employmenthistory.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.otus.svdovin.employmenthistory.domain.RecordType;
import ru.otus.svdovin.employmenthistory.dto.AuthRoleDto;
import ru.otus.svdovin.employmenthistory.dto.RecordTypeDto;
import ru.otus.svdovin.employmenthistory.exception.APIException;
import ru.otus.svdovin.employmenthistory.exception.ErrorCode;
import ru.otus.svdovin.employmenthistory.repository.RecordTypeRepository;
import ru.otus.svdovin.employmenthistory.repository.RecordRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Repository
public class RecordTypeProviderImpl implements RecordTypeProvider {

    private final RecordTypeRepository recordTypeRepository;
    private final RecordRepository recordRepository;
    private final MessageService messageService;

    public RecordTypeProviderImpl(RecordTypeRepository recordTypeRepository, RecordRepository recordRepository,
                                  MessageService messageService) {
        this.recordTypeRepository = recordTypeRepository;
        this.recordRepository = recordRepository;
        this.messageService = messageService;
    }

    @Override
    public RecordTypeDto getRecordType(long recordTypeId) {
        RecordType recordType = recordTypeRepository.findById(recordTypeId).orElse(null);
        if (recordType == null) {
            throw new APIException(
                    messageService.getFormatedMessage("employmentHistory.InvalidTypeCodeId", recordTypeId),
                    ErrorCode.INVALID_RECORDTYPE_ID.getCode()
            );
        }
        return recordType.buildDTO();
    }

    @Override
    public List<RecordTypeDto> getRecordTypeAll() {
        return recordTypeRepository.findAll(
                Sort.by(Sort.Direction.ASC, "recordTypeId"))
                .stream().map(e -> e.buildDTO()).collect(toList());
    }

    @Override
    public long createRecordType(RecordTypeDto recordTypeDto) {
        RecordType recordType = new RecordType(
                0,
                recordTypeDto.getTypeCode(),
                recordTypeDto.getTypeName());
        return recordTypeRepository.save(recordType).getRecordTypeId();
    }

    @Override
    public void updateRecordType(RecordTypeDto recordTypeDto) {
        RecordType recordType = recordTypeRepository.findById(recordTypeDto.getRecordTypeId()).orElse(null);
        if (recordType == null) {
            throw new APIException(
                    messageService.getMessage("employmentHistory.RecordTypeNotFound"),
                    ErrorCode.RECORDTYPE_NOT_FOUND.getCode()
            );
        }
        if (recordTypeDto.getTypeCode() != null) recordType.setTypeCode(recordTypeDto.getTypeCode());
        if (recordTypeDto.getTypeName() != null) recordType.setTypeName(recordTypeDto.getTypeName());
        recordTypeRepository.save(recordType);
    }

    @Override
    public void deleteRecordType(long recordTypeId) {
        RecordType recordType = recordTypeRepository.findById(recordTypeId).orElse(null);
        if (recordType == null) {
            throw new APIException(
                    messageService.getMessage("employmentHistory.RecordTypeNotFound"),
                    ErrorCode.EMPLOYEE_NOT_FOUND.getCode()
            );
        }
        boolean inUse = recordRepository.existsRecordByRecordTypeRecordTypeId(recordTypeId);
        if (inUse) {
            throw new APIException(
                    messageService.getMessage("employmentHistory.RecordTypeUseInRecord"),
                    ErrorCode.RECORDTYPE_USE_IN_RECORD.getCode()
            );
        }
        recordTypeRepository.deleteById(recordTypeId);
    }
}
