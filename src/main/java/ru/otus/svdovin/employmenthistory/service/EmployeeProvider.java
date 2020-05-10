package ru.otus.svdovin.employmenthistory.service;

import ru.otus.svdovin.employmenthistory.dto.EmployeeDto;

import java.util.List;

public interface EmployeeProvider {
    EmployeeDto getEmployee(long employeeId);
    List<EmployeeDto> getEmployeeAll();
    long createEmployee(EmployeeDto employeeDto);
    void updateEmployee(EmployeeDto employeeDto);
    void deleteEmployee(long employeeId);
}
