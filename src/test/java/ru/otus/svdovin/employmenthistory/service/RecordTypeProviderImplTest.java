package ru.otus.svdovin.employmenthistory.service;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import ru.otus.svdovin.employmenthistory.domain.AuthRole;
import ru.otus.svdovin.employmenthistory.domain.RecordType;
import ru.otus.svdovin.employmenthistory.dto.AuthRoleDto;
import ru.otus.svdovin.employmenthistory.dto.RecordTypeDto;
import ru.otus.svdovin.employmenthistory.repository.RecordTypeRepository;
import ru.otus.svdovin.employmenthistory.repository.RecordRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Сервис для работы с типами записей в трудовую книжку должен ")
@SpringBootTest(classes = {RecordTypeProviderImpl.class})
class RecordTypeProviderImplTest {

    private static final int RECORDTYPE_NEW_TYPECODE = 1;
    private static final String RECORDTYPE_NEW_TYPENAME = "New TypeName";
    private static final long NEW_RECORDTYPE_ID = 7;

    @MockBean
    private RecordTypeRepository recordTypeRepository;

    @MockBean
    private RecordRepository recordRepository;

    @MockBean
    private MessageService messageService;

    @Autowired
    private RecordTypeProvider recordTypeProvider;

    @Test
    @DisplayName("возвращать тип записи в трудовую книжку по id")
    void shouldReturnExpectedRecordTypeById() {
        val recordType = new RecordType(0, RECORDTYPE_NEW_TYPECODE, RECORDTYPE_NEW_TYPENAME);
        val recordTypeDto = recordType.buildDTO();
        Mockito.when(recordTypeRepository.findById(NEW_RECORDTYPE_ID)).thenReturn(Optional.of(recordType));
        RecordTypeDto actual = recordTypeProvider.getRecordType(NEW_RECORDTYPE_ID);
        assertThat(actual).isEqualToComparingFieldByField(recordTypeDto);
    }

    @Test
    @DisplayName("возвращать все типы записи в трудовую книжку")
    void shouldReturnAllAuthRoles() {
        val recordType = new RecordType(0, RECORDTYPE_NEW_TYPECODE, RECORDTYPE_NEW_TYPENAME);
        val recordTypeDto = recordType.buildDTO();
        List<RecordType> list = new ArrayList<>();
        list.add(recordType);
        Mockito.when(recordTypeRepository.findAll(Sort.by(Sort.Direction.ASC, "recordTypeId"))).thenReturn(list);
        List<RecordTypeDto> listAuthRoles = recordTypeProvider.getRecordTypeAll();
        assertAll("Тип записи",
                () -> assertThat(listAuthRoles.size()).isEqualTo(1),
                () -> assertThat(listAuthRoles.get(0)).isEqualToComparingFieldByField(recordTypeDto)
        );
    }

    @Test
    @DisplayName("должен корректно добавлять тип записи в трудовую книжку")
    void shouldCeateRecordType() {
        val recordType = new RecordType(NEW_RECORDTYPE_ID, RECORDTYPE_NEW_TYPECODE, RECORDTYPE_NEW_TYPENAME);
        val recordType2 = new RecordType(0, RECORDTYPE_NEW_TYPECODE, RECORDTYPE_NEW_TYPENAME);
        val recordTypeDto = recordType.buildDTO();
        Mockito.when(recordTypeRepository.save(recordType2)).thenReturn(recordType);
        long newId = recordTypeProvider.createRecordType(recordTypeDto);
        assertThat(newId).isEqualTo(NEW_RECORDTYPE_ID);
    }

    @Test
    @DisplayName("изменять тип записи в трудовую книжку")
    void shouldUpdateExpectedRecordType() {
        val recordType = new RecordType(NEW_RECORDTYPE_ID, RECORDTYPE_NEW_TYPECODE, RECORDTYPE_NEW_TYPENAME);
        val recordTypeDto = recordType.buildDTO();
        Mockito.when(recordTypeRepository.findById(NEW_RECORDTYPE_ID)).thenReturn(Optional.of(recordType));
        recordTypeProvider.updateRecordType(recordTypeDto);
        verify(recordTypeRepository, times(1)).save(recordType);
    }

    @Test
    @DisplayName("удалять ожидаемый тип записи в трудовую книжку")
    void shouldDeleteExpectedRecordTypeById() {
        val recordType = new RecordType(NEW_RECORDTYPE_ID, RECORDTYPE_NEW_TYPECODE, RECORDTYPE_NEW_TYPENAME);
        Mockito.when(recordTypeRepository.findById(NEW_RECORDTYPE_ID)).thenReturn(Optional.of(recordType));
        Mockito.when(recordRepository.existsRecordByRecordTypeRecordTypeId(NEW_RECORDTYPE_ID)).thenReturn(false);
        recordTypeProvider.deleteRecordType(NEW_RECORDTYPE_ID);
        verify(recordTypeRepository, times(1)).deleteById(NEW_RECORDTYPE_ID);
    }
}