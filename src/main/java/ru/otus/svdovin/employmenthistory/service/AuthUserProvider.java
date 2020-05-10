package ru.otus.svdovin.employmenthistory.service;

import ru.otus.svdovin.employmenthistory.dto.AuthUserDto;

import java.util.List;

public interface AuthUserProvider {
    AuthUserDto getAuthUser(long authUserId);
    List<AuthUserDto> getAuthUserAll();
    long createAuthUser(AuthUserDto authUserDto);
    void updateAuthUser(AuthUserDto authUserDto);
    void deleteAuthUser(long authUserId);
    void changeEnabledAuthUser(long authUserId, boolean isEnabled);
}
