package ru.otus.svdovin.employmenthistory.service;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import ru.otus.svdovin.employmenthistory.domain.Employee;
import ru.otus.svdovin.employmenthistory.domain.Record;
import ru.otus.svdovin.employmenthistory.domain.RecordType;
import ru.otus.svdovin.employmenthistory.dto.RecordDto;
import ru.otus.svdovin.employmenthistory.repository.EmployeeRepository;
import ru.otus.svdovin.employmenthistory.repository.RecordRepository;
import ru.otus.svdovin.employmenthistory.repository.RecordTypeRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Сервис для работы с записями трудовой книжки должен ")
@SpringBootTest(classes = {RecordProviderImpl.class})
class RecordProviderImplTest {

    private static final long RECORD_TYPE_NEW_ID = 1;
    private static final int RECORD_TYPE_NEW_CODE = 1;
    private static final String RECORD_TYPE_NEW_NAME = "New RecordTypeName";
    private static final long RECORD_NEW_ID = 4;
    private static final LocalDate RECORD_NEW_DATE = LocalDate.now();
    private static final String RECORD_NEW_POSITION = "New Position";
    private static final String RECORD_NEW_CODE = "New Code";
    private static final String RECORD_NEW_REASON = "New Reason";
    private static final String RECORD_NEW_DOCNAME = "New DocName";
    private static final LocalDate RECORD_NEW_DOCDATE = LocalDate.now();
    private static final String RECORD_NEW_DOCNUMBER = "New DocNumber";
    private static final String RECORD_NEW_CANCEL = "New Cancel";
    private static final String EMPLOYEE_NEW_LASTNAME = "New LastName";
    private static final String EMPLOYEE_NEW_FIRSTNAME = "New FirstName";
    private static final String EMPLOYEE_NEW_MIDDLENAME = "New MiddleName";
    private static final LocalDate EMPLOYEE_NEW_BIRTHDAY = LocalDate.now();
    private static final String EMPLOYEE_NEW_SNILS = "New Snils";
    private static final long NEW_EMPLOYEE_ID = 7;

    @MockBean
    private RecordRepository recordRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private RecordTypeRepository recordTypeRepository;

    @MockBean
    private MessageService messageService;

    @Autowired
    private RecordProvider recordProvider;

    @Test
    @DisplayName("возвращать ожидаемую запись по ее id")
    void shouldReturnExpectedRecordById() {
        val record = new Record(RECORD_NEW_ID, RECORD_NEW_DATE, RECORD_NEW_POSITION,
                RECORD_NEW_CODE, RECORD_NEW_REASON, RECORD_NEW_DOCNAME, RECORD_NEW_DOCDATE,
                RECORD_NEW_DOCNUMBER, RECORD_NEW_CANCEL);
        val recordDto = record.buildDTO();
        Mockito.when(recordRepository.findById(RECORD_NEW_ID)).thenReturn(Optional.of(record));
        RecordDto actual = recordProvider.getRecord(RECORD_NEW_ID);
        assertThat(actual).isEqualToComparingFieldByField(recordDto);
    }

    @Test
    @DisplayName("возвращать записи по id сотрудника")
    void shouldReturnRecordsByEmployeeId() {
        val record = new Record(RECORD_NEW_ID, RECORD_NEW_DATE, RECORD_NEW_POSITION,
                RECORD_NEW_CODE, RECORD_NEW_REASON, RECORD_NEW_DOCNAME, RECORD_NEW_DOCDATE,
                RECORD_NEW_DOCNUMBER, RECORD_NEW_CANCEL);
        val recordDto = record.buildDTO();
        List<Record> list = new ArrayList<>();
        list.add(record);
        Mockito.when(recordRepository.findByEmployeeEmployeeId(NEW_EMPLOYEE_ID,
                Sort.by(Sort.Direction.ASC, "recordId"))).thenReturn(list);
        List<RecordDto> listRecords = recordProvider.getRecordsByEmployeeId(NEW_EMPLOYEE_ID);
        assertAll("Запись",
                () -> assertThat(listRecords.size()).isEqualTo(1),
                () -> assertThat(listRecords.get(0)).isEqualToComparingFieldByField(recordDto)
        );
    }

    @Test
    @DisplayName("должен корректно добавлять запись")
    void shouldCeateRecord() {
        val employee = new Employee(NEW_EMPLOYEE_ID, EMPLOYEE_NEW_LASTNAME, EMPLOYEE_NEW_FIRSTNAME,
                EMPLOYEE_NEW_MIDDLENAME, EMPLOYEE_NEW_BIRTHDAY, EMPLOYEE_NEW_SNILS);
        val recordType = new RecordType(RECORD_TYPE_NEW_ID, RECORD_TYPE_NEW_CODE, RECORD_TYPE_NEW_NAME);
        val record = new Record(RECORD_NEW_ID, RECORD_NEW_DATE, RECORD_NEW_POSITION,
                RECORD_NEW_CODE, RECORD_NEW_REASON, RECORD_NEW_DOCNAME, RECORD_NEW_DOCDATE,
                RECORD_NEW_DOCNUMBER, RECORD_NEW_CANCEL, employee, recordType);
        val record2 = new Record(0L, RECORD_NEW_DATE, RECORD_NEW_POSITION,
                RECORD_NEW_CODE, RECORD_NEW_REASON, RECORD_NEW_DOCNAME, RECORD_NEW_DOCDATE,
                RECORD_NEW_DOCNUMBER, RECORD_NEW_CANCEL, employee, recordType);
        val recordDto = record.buildDTO();
        Mockito.when(recordTypeRepository.findById(RECORD_TYPE_NEW_ID)).thenReturn(Optional.of(recordType));
        Mockito.when(employeeRepository.findById(NEW_EMPLOYEE_ID)).thenReturn(Optional.of(employee));
        Mockito.when(recordRepository.save(record2)).thenReturn(record);
        long newId = recordProvider.createRecord(recordDto);
        assertThat(newId).isEqualTo(RECORD_NEW_ID);
    }

    @Test
    @DisplayName("изменять ожидаемую запись")
    void shouldUpdateExpectedRecord() {
        val record = new Record(RECORD_NEW_ID, RECORD_NEW_DATE, RECORD_NEW_POSITION,
                RECORD_NEW_CODE, RECORD_NEW_REASON, RECORD_NEW_DOCNAME, RECORD_NEW_DOCDATE,
                RECORD_NEW_DOCNUMBER, RECORD_NEW_CANCEL);
        val recordDto = record.buildDTO();
        Mockito.when(recordRepository.findById(RECORD_NEW_ID)).thenReturn(Optional.of(record));
        recordProvider.updateRecord(recordDto);
        verify(recordRepository, times(1)).save(record);
    }

    @Test
    @DisplayName("удалять ожидаемую запись")
    void shouldDeleteExpectedRecordById() {
        val record = new Record(RECORD_NEW_ID, RECORD_NEW_DATE, RECORD_NEW_POSITION,
                RECORD_NEW_CODE, RECORD_NEW_REASON, RECORD_NEW_DOCNAME, RECORD_NEW_DOCDATE,
                RECORD_NEW_DOCNUMBER, RECORD_NEW_CANCEL);
        Mockito.when(recordRepository.findById(RECORD_NEW_ID)).thenReturn(Optional.of(record));
        recordProvider.deleteRecord(RECORD_NEW_ID);
        verify(recordRepository, times(1)).deleteById(RECORD_NEW_ID);
    }
}
