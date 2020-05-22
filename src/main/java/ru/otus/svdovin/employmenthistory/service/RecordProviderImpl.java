package ru.otus.svdovin.employmenthistory.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.svdovin.employmenthistory.domain.Employee;
import ru.otus.svdovin.employmenthistory.domain.Record;
import ru.otus.svdovin.employmenthistory.domain.RecordType;
import ru.otus.svdovin.employmenthistory.dto.RecordDto;
import ru.otus.svdovin.employmenthistory.exception.APIException;
import ru.otus.svdovin.employmenthistory.exception.ErrorCode;
import ru.otus.svdovin.employmenthistory.repository.EmployeeRepository;
import ru.otus.svdovin.employmenthistory.repository.RecordRepository;
import ru.otus.svdovin.employmenthistory.repository.RecordTypeRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class RecordProviderImpl implements RecordProvider {

    private final RecordRepository recordRepository;
    private final RecordTypeRepository recordTypeRepository;
    private final EmployeeRepository employeeRepository;
    private final MessageService messageService;

    public RecordProviderImpl(RecordRepository recordRepository, RecordTypeRepository recordTypeRepository,
                              EmployeeRepository employeeRepository, MessageService messageService) {
        this.recordRepository = recordRepository;
        this.recordTypeRepository = recordTypeRepository;
        this.employeeRepository = employeeRepository;
        this.messageService = messageService;
    }

    @Override
    public RecordDto getRecord(long recordId) {
        Record record = recordRepository.findById(recordId).orElse(null);
        if (record == null) {
            throw new APIException(
                    messageService.getFormatedMessage("employmentHistory.InvalidRecordId", recordId),
                    ErrorCode.INVALID_RECORD_ID.getCode()
            );
        }
        return RecordDto.buildDTO(record);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RecordDto> getRecordsByEmployeeId(long employeeId) {
        return recordRepository.findByEmployeeEmployeeId(employeeId, Sort.by(Sort.Direction.ASC, "recordId"))
                .stream().map(r -> RecordDto.buildDTO(r)).collect(toList());
    }

    @Transactional
    @Override
    public long createRecord(RecordDto recordDto) {
        Long typeCodeId = recordDto.getTypeCodeId();
        if (typeCodeId == null) {
            throw new APIException(
                    messageService.getMessage("employmentHistory.InvalidTypeCodeId"),
                    ErrorCode.INVALID_TYPECODE_ID.getCode()
            );
        }
        RecordType recordType = recordTypeRepository.findById(typeCodeId).orElse(null);
        if (recordType == null) {
            throw new APIException(
                    messageService.getMessage("employmentHistory.RecordTypeNotFound"),
                    ErrorCode.RECORDTYPE_NOT_FOUND.getCode()
            );
        }
        Long employeeId = recordDto.getEmployeeId();
        if (employeeId == null) {
            throw new APIException(
                    messageService.getMessage("employmentHistory.InvalidEmployeeId"),
                    ErrorCode.INVALID_EMPLOYEE_ID.getCode()
            );
        }
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            throw new APIException(
                    messageService.getMessage("employmentHistory.EmployeeNotFound"),
                    ErrorCode.EMPLOYEE_NOT_FOUND.getCode()
            );
        }
        Record record = new Record(
                0,
                recordDto.getDate(),
                recordDto.getPosition(),
                recordDto.getCode(),
                recordDto.getReason(),
                recordDto.getDocName(),
                recordDto.getDocDate(),
                recordDto.getDocNumber(),
                recordDto.getCancel());
        record.setRecordType(recordType);
        record.setEmployee(employee);
        return recordRepository.save(record).getRecordId();
    }

    @Transactional
    @Override
    public void updateRecord(RecordDto recordDto) {
        Record record = recordRepository.findById(recordDto.getRecordId()).orElse(null);
        if (record == null) {
            throw new APIException(
                    messageService.getMessage("employmentHistory.RecordNotFound"),
                    ErrorCode.RECORD_NOT_FOUND.getCode()
            );
        }
        if (recordDto.getTypeCodeId() != null) {
            RecordType recordType = recordTypeRepository.findById(recordDto.getTypeCodeId()).orElse(null);
            if (recordType != null) {
                record.setRecordType(recordType);
            }
        }
        if (recordDto.getDate() != null) record.setDate(recordDto.getDate());
        if (recordDto.getPosition() != null) record.setPosition(recordDto.getPosition());
        if (recordDto.getCode() != null) record.setCode(recordDto.getCode());
        if (recordDto.getReason() != null) record.setReason(recordDto.getReason());
        if (recordDto.getDocName() != null) record.setDocName(recordDto.getDocName());
        if (recordDto.getDocDate() != null) record.setDocDate(recordDto.getDocDate());
        if (recordDto.getDocNumber() != null) record.setDocNumber(recordDto.getDocNumber());
        if (recordDto.getCancel() != null) record.setCancel(recordDto.getCancel());
        recordRepository.save(record);
    }

    @Transactional
    @Override
    public void deleteRecord(long recordId) {
        Record record = recordRepository.findById(recordId).orElse(null);
        if (record == null) {
            throw new APIException(
                    messageService.getMessage("employmentHistory.RecordNotFound"),
                    ErrorCode.RECORD_NOT_FOUND.getCode()
            );
        }
        recordRepository.deleteById(recordId);
    }
}
