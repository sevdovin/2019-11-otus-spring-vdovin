package ru.otus.svdovin.employmenthistory.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.svdovin.employmenthistory.domain.RecordType;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с типами записей в трудовую книжку должен ")
@DataJpaTest
class RecordTypeRepositoryImplTest {

    private static final int RECORDTYPE_NEW_TYPECODE = 1;
    private static final String RECORDTYPE_NEW_TYPENAME = "New TypeName";
    private static final long RECORDTYPE_ID_EXIST = 3;
    private static final String RECORDTYPE_TYPENAME_EXISTS = "Переименование";
    private static final int RECORDTYPE_COUNT_INT = 6;

    @Autowired
    private RecordTypeRepository recordTypeRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("добавлять тип записи в трудовую книжку в БД")
    @Test
    void shouldInsertRecordType() {
        val recordType = new RecordType(0, RECORDTYPE_NEW_TYPECODE, RECORDTYPE_NEW_TYPENAME);
        long newId = recordTypeRepository.save(recordType).getRecordTypeId();
        recordType.setRecordTypeId(newId);
        val actual = em.find(RecordType.class, newId);
        assertThat(actual).isNotNull().isEqualToComparingFieldByField(recordType);
    }

    @DisplayName("изменять тип записи в трудовую книжку")
    @Test
    void shouldUpdateRecordTypeName() {
        RecordType recordType1 = em.find(RecordType.class, RECORDTYPE_ID_EXIST);
        String oldName = recordType1.getTypeName();
        em.detach(recordType1);
        recordType1.setTypeName(RECORDTYPE_NEW_TYPENAME);
        RecordType recordType2 = recordTypeRepository.save(recordType1);
        assertThat(recordType2.getTypeName()).isNotEqualTo(oldName).isEqualTo(RECORDTYPE_NEW_TYPENAME);
    }

    @DisplayName("удалять тип записи в трудовую книжку")
    @Test
    void shouldDeleteRecordType() {
        val recordType1 = em.find(RecordType.class, RECORDTYPE_ID_EXIST);
        assertThat(recordType1).isNotNull();
        em.detach(recordType1);
        recordTypeRepository.deleteById(RECORDTYPE_ID_EXIST);
        val recordType2 = em.find(RecordType.class, RECORDTYPE_ID_EXIST);
        assertThat(recordType2).isNull();
    }

    @DisplayName("возвращать тип записи в трудовую книжку по его id")
    @Test
    void shouldFindRecordTypeById() {
        Optional<RecordType> recordType = recordTypeRepository.findById(RECORDTYPE_ID_EXIST);
        assertThat(recordType).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("typeName", RECORDTYPE_TYPENAME_EXISTS);
    }

    @DisplayName("возвращать все типы записи в трудовую книжку")
    @Test
    void shouldFindEmployeeAll() {
        List<RecordType> recordTypes = recordTypeRepository.findAll();
        assertThat(recordTypes).isNotNull().hasSize(RECORDTYPE_COUNT_INT);
    }
}
