package ru.otus.svdovin.employmenthistory.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.otus.svdovin.employmenthistory.domain.AuthUser;
import ru.otus.svdovin.employmenthistory.dto.AuthUserDto;
import ru.otus.svdovin.employmenthistory.exception.APIException;
import ru.otus.svdovin.employmenthistory.exception.ErrorCode;
import ru.otus.svdovin.employmenthistory.repository.AuthUserRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Repository
public class AuthUserProviderImpl implements AuthUserProvider {

    private final AuthUserRepository authUserRepository;
    private final MessageService messageService;

    public AuthUserProviderImpl(AuthUserRepository authUserRepository, MessageService messageService) {
        this.authUserRepository = authUserRepository;
        this.messageService = messageService;
    }

    @Override
    public AuthUserDto getAuthUser(long authUserId) {
        AuthUser authUser = authUserRepository.findById(authUserId).orElse(null);
        if (authUser == null) {
            throw new APIException(
                    messageService.getFormatedMessage("employmentHistory.InvalidAuthUserId", authUserId),
                    ErrorCode.INVALID_AUTHROLE_ID.getCode()
            );
        }
        return authUser.buildDTO();
    }

    @Override
    public List<AuthUserDto> getAuthUserAll() {
        return authUserRepository.findAll(
                Sort.by(Sort.Direction.ASC, "userId"))
                .stream().map(e -> e.buildDTO()).collect(toList());
    }

    @Override
    public long createAuthUser(AuthUserDto authUserDto) {
        AuthUser authUser = new AuthUser(
                0,
                authUserDto.getEmployeeId(),
                authUserDto.getLogin(),
                authUserDto.getPassword(),
                authUserDto.getIsEnabled(),
                authUserDto.getEmail());
        return authUserRepository.save(authUser).getUserId();
    }

    @Override
    public void updateAuthUser(AuthUserDto authUserDto) {
        AuthUser authUser = authUserRepository.findById(authUserDto.getUserId()).orElse(null);
        if (authUser == null) {
            throw new APIException(
                    messageService.getMessage("employmentHistory.AuthUserNotFound"),
                    ErrorCode.AUTHUSER_NOT_FOUND.getCode()
            );
        }
        if (authUserDto.getEmployeeId() != null) authUser.setEmployeeId(authUserDto.getEmployeeId());
        if (authUserDto.getLogin() != null) authUser.setLogin(authUserDto.getLogin());
        if (authUserDto.getPassword() != null) authUser.setPassword(authUserDto.getPassword());
        if (authUserDto.getIsEnabled() != null) authUser.setIsEnabled(authUserDto.getIsEnabled());
        if (authUserDto.getEmail() != null) authUser.setEmail(authUserDto.getEmail());
        authUserRepository.save(authUser);
    }

    @Override
    public void deleteAuthUser(long authUserId) {
        AuthUser authUser = authUserRepository.findById(authUserId).orElse(null);
        if (authUser == null) {
            throw new APIException(
                    messageService.getMessage("employmentHistory.AuthUserNotFound"),
                    ErrorCode.AUTHUSER_NOT_FOUND.getCode()
            );
        }
        authUserRepository.deleteById(authUserId);
    }

    @Override
    public void changeEnabledAuthUser(long authUserId, boolean isEnabled) {
        AuthUser authUser = authUserRepository.findById(authUserId).orElse(null);
        if (authUser == null) {
            throw new APIException(
                    messageService.getFormatedMessage("employmentHistory.InvalidAuthUserId", authUserId),
                    ErrorCode.INVALID_AUTHROLE_ID.getCode()
            );
        }
        authUser.setIsEnabled(isEnabled);
        authUserRepository.save(authUser);
    }
}
