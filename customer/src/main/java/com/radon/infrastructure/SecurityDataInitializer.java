package com.radon.infrastructure;

import com.radon.application.port.in.CreateAuthorityUseCase;
import com.radon.application.port.in.CreateRoleUseCase;
import com.radon.domain.Authority;
import com.radon.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class SecurityDataInitializer implements CommandLineRunner {

    private final CreateRoleUseCase createRoleUseCase;
    private final CreateAuthorityUseCase createAuthorityUseCase;


    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Authority authorityRead = createAuthorityUseCase.createAuthority(Authority.builder().authority("product:read").build());
        Authority authorityCreate = createAuthorityUseCase.createAuthority(Authority.builder().authority("product:create").build());
        Authority authorityDelete = createAuthorityUseCase.createAuthority(Authority.builder().authority("product:delete").build());
        Authority authorityUpdate = createAuthorityUseCase.createAuthority(Authority.builder().authority("product:update").build());

        Role admin = createRoleUseCase.createRole(Role.builder().role("ADMIN").authorities(Set.of(
                authorityRead,
                authorityCreate,
                authorityDelete,
                authorityUpdate
        )).build());

        Role user = createRoleUseCase.createRole(Role.builder().role("USER").authorities(Set.of(
                authorityRead
        )).build());

    }
}
