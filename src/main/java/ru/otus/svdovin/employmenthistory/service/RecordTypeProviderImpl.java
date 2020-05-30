package ru.otus.svdovin.employmenthistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.svdovin.employmenthistory.domain.RecordType;
import ru.otus.svdovin.employmenthistory.dto.RecordTypeDto;
import ru.otus.svdovin.employmenthistory.exception.APIException;
import ru.otus.svdovin.employmenthistory.exception.ErrorCode;
import ru.otus.svdovin.employmenthistory.repository.RecordTypeRepository;
import ru.otus.svdovin.employmenthistory.repository.RecordRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class RecordTypeProviderImpl implements RecordTypeProvider {

    private final RecordTypeRepository recordTypeRepository;
    private final RecordRepository recordRepository;
    private final MessageService messageService;

    @Transactional(readOnly = true)
    @Override
    public RecordTypeDto getRecordType(long recordTypeId) {
        RecordType recordType = recordTypeRepository.findById(recordTypeId).orElse(null);
        if (recordType == null) {
            throw new APIException(
                    messageService.getFormatedMessage("employmentHistory.InvalidTypeCodeId", recordTypeId),
                    ErrorCode.INVALID_RECORDTYPE_ID.getCode()
            );
        }
        return RecordTypeDto.buildDTO(recordType);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RecordTypeDto> getRecordTypeAll() {
        return recordTypeRepository.findAll(
                Sort.by(Sort.Direction.ASC, "recordTypeId"))
                .stream().map(e -> RecordTypeDto.buildDTO(e)).collect(toList());
    }

    @Transactional
    @Override
    public long createRecordType(RecordTypeDto recordTypeDto) {
        RecordType recordType = new RecordType(
                0,
                recordTypeDto.getTypeCode(),
                recordTypeDto.getTypeName());
        return recordTypeRepository.save(recordType).getRecordTypeId();
    }

    @Transactional
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

    @Transactional
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
