package ru.otus.svdovin.employmenthistory.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.otus.svdovin.employmenthistory.domain.Company;
import ru.otus.svdovin.employmenthistory.domain.Employee;
import ru.otus.svdovin.employmenthistory.dto.EmployeeDto;
import ru.otus.svdovin.employmenthistory.exception.APIException;
import ru.otus.svdovin.employmenthistory.exception.ErrorCode;
import ru.otus.svdovin.employmenthistory.repository.CompanyRepository;
import ru.otus.svdovin.employmenthistory.repository.EmployeeRepository;
import ru.otus.svdovin.employmenthistory.repository.RecordRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Repository
public class EmployeeProviderImpl implements EmployeeProvider {

    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final RecordRepository recordRepository;
    private final MessageService messageService;

    public EmployeeProviderImpl(EmployeeRepository employeeRepository, CompanyRepository companyRepository,
                                RecordRepository recordRepository, MessageService messageService) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
        this.recordRepository = recordRepository;
        this.messageService = messageService;
    }

    @Override
    public EmployeeDto getEmployee(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            throw new APIException(
                    messageService.getFormatedMessage("employmentHistory.InvalidEmployeeId", employeeId),
                    ErrorCode.INVALID_EMPLOYEE_ID.getCode()
            );
        }
        return employee.buildDTO();
    }

    @Override
    public List<EmployeeDto> getEmployeeAll() {
        return employeeRepository.findAll(
                Sort.by(Sort.Direction.ASC, "lastName").and(
                        Sort.by(Sort.Direction.ASC, "firstName")))
                .stream().map(e -> e.buildDTO()).collect(toList());
    }

    @Override
    public long createEmployee(EmployeeDto employeeDto) {
        Long companyId = employeeDto.getCompanyId();
        if (companyId == null) {
            throw new APIException(
                    messageService.getMessage("employmentHistory.InvalidCompanyId"),
                    ErrorCode.INVALID_COMPANY_ID.getCode()
            );
        }
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company == null) {
            throw new APIException(
                    messageService.getMessage("employmentHistory.CompanyNotFound"),
                    ErrorCode.COMPANY_NOT_FOUND.getCode()
            );
        }
        Employee employee = new Employee(
                0,
                employeeDto.getLastName(),
                employeeDto.getFirstName(),
                employeeDto.getMiddleName(),
                employeeDto.getBirthday(),
                employeeDto.getSnils());
        employee.setCompany(company);
        return employeeRepository.save(employee).getEmployeeId();
    }

    @Override
    public void updateEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(employeeDto.getEmployeeId()).orElse(null);
        if (employee == null) {
            throw new APIException(
                    messageService.getMessage("employmentHistory.EmployeeNotFound"),
                    ErrorCode.EMPLOYEE_NOT_FOUND.getCode()
            );
        }
        if (employeeDto.getLastName() != null) employee.setLastName(employeeDto.getLastName());
        if (employeeDto.getFirstName() != null) employee.setFirstName(employeeDto.getFirstName());
        if (employeeDto.getMiddleName() != null) employee.setMiddleName(employeeDto.getMiddleName());
        if (employeeDto.getBirthday() != null) employee.setBirthday(employeeDto.getBirthday());
        if (employeeDto.getSnils() != null) employee.setSnils(employeeDto.getSnils());
        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            throw new APIException(
                    messageService.getMessage("employmentHistory.EmployeeNotFound"),
                    ErrorCode.EMPLOYEE_NOT_FOUND.getCode()
            );
        }
        boolean inUse = recordRepository.existsRecordByEmployeeEmployeeId(employeeId);
        if (inUse) {
            throw new APIException(
                    messageService.getMessage("employmentHistory.EmployeeUseInRecord"),
                    ErrorCode.EMPLOYEE_USE_IN_RECORD.getCode()
            );
        }
        employeeRepository.deleteById(employeeId);
    }
}
