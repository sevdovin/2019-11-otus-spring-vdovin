package ru.otus.svdovin.employmenthistory.service;

import ru.otus.svdovin.employmenthistory.dto.AuthRoleDto;

import java.util.List;

public interface AuthRoleProvider {
    AuthRoleDto getAuthRole(long authRoleId);
    List<AuthRoleDto> getAuthRoleAll();
    long createAuthRole(AuthRoleDto authRoleDto);
    void updateAuthRole(AuthRoleDto authRoleDto);
    void deleteAuthRole(long authRoleId);
}
