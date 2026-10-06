package com.radon.application.service;

import com.radon.application.port.in.CheckRolesExistsUseCase;
import com.radon.application.port.in.CreateAuthorityUseCase;
import com.radon.application.port.in.CreateRoleUseCase;
import com.radon.application.port.out.AuthorizationRepository;
import com.radon.domain.Authority;
import com.radon.domain.Role;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements CreateAuthorityUseCase, CreateRoleUseCase, CheckRolesExistsUseCase {

    private final AuthorizationRepository authorizationRepository;

    public AuthorizationService(AuthorizationRepository authorizationRepository) {
        this.authorizationRepository = authorizationRepository;
    }

    @Override
    public Authority createAuthority(Authority authority) {
        return authorizationRepository.createAuthority(authority);
    }

    @Override
    public Role createRole(Role role) {
        return authorizationRepository.createRole(role);
    }

    @Override
    public Boolean checkRolesExists(Role role) {
        return authorizationRepository.checkRolesExists(role);
    }
}
