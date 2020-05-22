package ru.otus.svdovin.employmenthistory.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.svdovin.employmenthistory.domain.AuthUser;
import ru.otus.svdovin.employmenthistory.dto.AuthUserDto;
import ru.otus.svdovin.employmenthistory.exception.APIException;
import ru.otus.svdovin.employmenthistory.exception.ErrorCode;
import ru.otus.svdovin.employmenthistory.repository.AuthUserRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AuthUserProviderImpl implements AuthUserProvider {

    private final AuthUserRepository authUserRepository;
    private final MessageService messageService;

    public AuthUserProviderImpl(AuthUserRepository authUserRepository, MessageService messageService) {
        this.authUserRepository = authUserRepository;
        this.messageService = messageService;
    }

    @Transactional(readOnly = true)
    @Override
    public AuthUserDto getAuthUser(long authUserId) {
        AuthUser authUser = authUserRepository.findById(authUserId).orElse(null);
        if (authUser == null) {
            throw new APIException(
                    messageService.getFormatedMessage("employmentHistory.InvalidAuthUserId", authUserId),
                    ErrorCode.INVALID_AUTHUSER_ID.getCode()
            );
        }
        return AuthUserDto.buildDTO(authUser);
    }

    @Transactional(readOnly = true)
    @Override
    public AuthUserDto getAuthUserByLogin(String login) {
        AuthUser authUser = authUserRepository.findByLogin(login).orElse(null);
        if (authUser == null) {
            throw new APIException(
                    messageService.getFormatedMessage("employmentHistory.AuthUserNotFound"),
                    ErrorCode.AUTHUSER_NOT_FOUND.getCode()
            );
        }
        return AuthUserDto.buildDTO(authUser);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AuthUserDto> getAuthUserAll() {
        return authUserRepository.findAll(
                Sort.by(Sort.Direction.ASC, "userId"))
                .stream().map(e -> AuthUserDto.buildDTO(e)).collect(toList());
    }

    @Transactional
    @Override
    public long createAuthUser(AuthUserDto authUserDto) {
        AuthUser authUser = new AuthUser(
                0,
                authUserDto.getLogin(),
                authUserDto.getPassword(),
                authUserDto.getIsEnabled(),
                authUserDto.getEmail());
        return authUserRepository.save(authUser).getUserId();
    }

    @Transactional
    @Override
    public void updateAuthUser(AuthUserDto authUserDto) {
        AuthUser authUser = authUserRepository.findById(authUserDto.getUserId()).orElse(null);
        if (authUser == null) {
            throw new APIException(
                    messageService.getMessage("employmentHistory.AuthUserNotFound"),
                    ErrorCode.AUTHUSER_NOT_FOUND.getCode()
            );
        }
        if (authUserDto.getLogin() != null) authUser.setLogin(authUserDto.getLogin());
        if (authUserDto.getPassword() != null) authUser.setPassword(authUserDto.getPassword());
        if (authUserDto.getIsEnabled() != null) authUser.setIsEnabled(authUserDto.getIsEnabled());
        if (authUserDto.getEmail() != null) authUser.setEmail(authUserDto.getEmail());
        authUserRepository.save(authUser);
    }

    @Transactional
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

    @Transactional
    @Override
    public void changeEnabledAuthUser(long authUserId, boolean isEnabled) {
        AuthUser authUser = authUserRepository.findById(authUserId).orElse(null);
        if (authUser == null) {
            throw new APIException(
                    messageService.getFormatedMessage("employmentHistory.InvalidAuthUserId", authUserId),
                    ErrorCode.INVALID_AUTHUSER_ID.getCode()
            );
        }
        authUser.setIsEnabled(isEnabled);
        authUserRepository.save(authUser);
    }
}
