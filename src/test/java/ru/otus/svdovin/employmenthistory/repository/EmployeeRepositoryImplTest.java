package ru.otus.svdovin.employmenthistory.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.svdovin.employmenthistory.domain.Employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с сотрудниками должен ")
@DataJpaTest
class EmployeeRepositoryImplTest {

    private static final String EMPLOYEE_NEW_LASTNAME = "New LastName";
    private static final String EMPLOYEE_NEW_FIRSTNAME = "New FirstName";
    private static final String EMPLOYEE_NEW_MIDDLENAME = "New MiddleName";
    private static final LocalDate EMPLOYEE_NEW_BIRTHDAY = LocalDate.now();
    private static final String EMPLOYEE_NEW_SNILS = "New Snils";
    private static final long EMPLOYEE_ID_EXIST = 3;
    private static final String EMPLOYEE_LASTNAME_EXISTS = "Сидоров";
    private static final int EMPLOYEES_COUNT_INT = 3;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("добавлять сотрудника в БД")
    @Test
    void shouldInsertEmployee() {
        val employee = new Employee(0, EMPLOYEE_NEW_LASTNAME, EMPLOYEE_NEW_FIRSTNAME,
                EMPLOYEE_NEW_MIDDLENAME, EMPLOYEE_NEW_BIRTHDAY, EMPLOYEE_NEW_SNILS);
        long newId = employeeRepository.save(employee).getEmployeeId();
        employee.setEmployeeId(newId);
        val actual = em.find(Employee.class, newId);
        assertThat(actual).isNotNull().isEqualToComparingFieldByField(employee);
    }

    @DisplayName("изменять фамилию сотрудника")
    @Test
    void shouldUpdateEmployeeName() {
        Employee employee1 = em.find(Employee.class, EMPLOYEE_ID_EXIST);
        String oldName = employee1.getLastName();
        em.detach(employee1);
        employee1.setLastName(EMPLOYEE_NEW_LASTNAME);
        Employee employee2 = employeeRepository.save(employee1);
        assertThat(employee2.getLastName()).isNotEqualTo(oldName).isEqualTo(EMPLOYEE_NEW_LASTNAME);
    }

    @DisplayName("удалять сотрудника")
    @Test
    void shouldDeleteEmployee() {
        val employee1 = em.find(Employee.class, EMPLOYEE_ID_EXIST);
        assertThat(employee1).isNotNull();
        em.detach(employee1);
        employeeRepository.deleteById(EMPLOYEE_ID_EXIST);
        val employee2 = em.find(Employee.class, EMPLOYEE_ID_EXIST);
        assertThat(employee2).isNull();
    }

    @DisplayName("возвращать сотрудника по его id")
    @Test
    void shouldFindEmployeeById() {
        Optional<Employee> employee = employeeRepository.findById(EMPLOYEE_ID_EXIST);
        assertThat(employee).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("lastName", EMPLOYEE_LASTNAME_EXISTS);
    }

    @DisplayName("возвращать список всех сотрудников")
    @Test
    void shouldFindEmployeeAll() {
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).isNotNull().hasSize(EMPLOYEES_COUNT_INT);
    }
}
