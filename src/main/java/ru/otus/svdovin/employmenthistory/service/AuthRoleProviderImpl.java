package ru.otus.svdovin.employmenthistory.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.svdovin.employmenthistory.domain.AuthRole;
import ru.otus.svdovin.employmenthistory.dto.AuthRoleDto;
import ru.otus.svdovin.employmenthistory.exception.APIException;
import ru.otus.svdovin.employmenthistory.exception.ErrorCode;
import ru.otus.svdovin.employmenthistory.repository.AuthRoleRepository;
import ru.otus.svdovin.employmenthistory.repository.AuthUserRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AuthRoleProviderImpl implements AuthRoleProvider {

    private final AuthRoleRepository authRoleRepository;
    private final AuthUserRepository authUserRepository;
    private final MessageService messageService;

    public AuthRoleProviderImpl(AuthRoleRepository authRoleRepository, AuthUserRepository authUserRepository,
                                  MessageService messageService) {
        this.authRoleRepository = authRoleRepository;
        this.authUserRepository = authUserRepository;
        this.messageService = messageService;
    }

    @Transactional(readOnly = true)
    @Override
    public AuthRoleDto getAuthRole(long authRoleId) {
        AuthRole authRole = authRoleRepository.findById(authRoleId).orElse(null);
        if (authRole == null) {
            throw new APIException(
                    messageService.getFormatedMessage("employmentHistory.InvalidAuthRoleId", authRoleId),
                    ErrorCode.INVALID_AUTHROLE_ID.getCode()
            );
        }
        return AuthRoleDto.buildDTO(authRole);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AuthRoleDto> getAuthRoleAll() {
        return authRoleRepository.findAll(
                Sort.by(Sort.Direction.ASC, "roleId"))
                .stream().map(e -> AuthRoleDto.buildDTO(e)).collect(toList());
    }

    @Transactional
    @Override
    public void updateAuthRole(AuthRoleDto authRoleDto) {
        AuthRole authRole = authRoleRepository.findById(authRoleDto.getRoleId()).orElse(null);
        if (authRole == null) {
            throw new APIException(
                    messageService.getMessage("employmentHistory.AuthRoleNotFound"),
                    ErrorCode.AUTHROLE_NOT_FOUND.getCode()
            );
        }
        if (authRoleDto.getRoleSysName() != null) authRole.setRoleSysName(authRoleDto.getRoleSysName());
        if (authRoleDto.getRoleName() != null) authRole.setRoleName(authRoleDto.getRoleName());
        authRoleRepository.save(authRole);
    }
}
