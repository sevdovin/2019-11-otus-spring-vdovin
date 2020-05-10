package ru.otus.svdovin.employmenthistory.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import ru.otus.svdovin.employmenthistory.domain.Record;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с записями трудовых книжек должен ")
@DataJpaTest
class RecordRepositoryImplTest {

    private static final LocalDate RECORD_NEW_DATE = LocalDate.now();
    private static final String RECORD_NEW_POSITION = "New Position";
    private static final String RECORD_NEW_CODE = "New Code";
    private static final String RECORD_NEW_REASON = "New Reason";
    private static final String RECORD_NEW_DOCNAME = "New DocName";
    private static final LocalDate RECORD_NEW_DOCDATE = LocalDate.now();
    private static final String RECORD_NEW_DOCNUMBER = "New DocNumber";
    private static final String RECORD_NEW_CANCEL = "New Cancel";
    private static final long RECORD_ID_EXIST = 1;
    private static final String RECORD_POSITION_EXISTS = "Экономист";
    private static final long EMPLOYEE_ID_EXIST = 1;
    private static final int RECORDS_EMPLOYEE_COUNT_INT = 2;

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("добавлять запись в БД")
    @Test
    void shouldInsertRecord() {
        val record = new Record(0, RECORD_NEW_DATE, RECORD_NEW_POSITION, RECORD_NEW_CODE, RECORD_NEW_REASON,
                RECORD_NEW_DOCNAME, RECORD_NEW_DOCDATE, RECORD_NEW_DOCNUMBER, RECORD_NEW_CANCEL);
        long newId = recordRepository.save(record).getRecordId();
        record.setRecordId(newId);
        val actual = em.find(Record.class, newId);
        assertThat(actual).isNotNull().isEqualToComparingFieldByField(record);
    }

    @DisplayName("изменять запись")
    @Test
    void shouldUpdateRecordPosition() {
        Record record1 = em.find(Record.class, RECORD_ID_EXIST);
        String oldPosition = record1.getPosition();
        em.detach(record1);
        record1.setPosition(RECORD_NEW_POSITION);
        Record record2 = recordRepository.save(record1);
        assertThat(record2.getPosition()).isNotEqualTo(oldPosition).isEqualTo(RECORD_NEW_POSITION);
    }

    @DisplayName("удалять запись")
    @Test
    void shouldDeleteRecord() {
        val record1 = em.find(Record.class, RECORD_ID_EXIST);
        assertThat(record1).isNotNull();
        em.detach(record1);
        recordRepository.deleteById(RECORD_ID_EXIST);
        val record2 = em.find(Record.class, RECORD_ID_EXIST);
        assertThat(record2).isNull();
    }

    @DisplayName("возвращать запись по id")
    @Test
    void shouldFindRecordById() {
        Optional<Record> record = recordRepository.findById(RECORD_ID_EXIST);
        assertThat(record).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("position", RECORD_POSITION_EXISTS);
    }

    @DisplayName("возвращать записи по id сотрудника")
    @Test
    void shouldFindRecordsByEmployeeId() {
        List<Record> records = recordRepository.findByEmployeeEmployeeId(EMPLOYEE_ID_EXIST,
                Sort.by(Sort.Direction.ASC, "recordId"));
        assertThat(records).isNotNull().hasSize(RECORDS_EMPLOYEE_COUNT_INT);
    }
}
