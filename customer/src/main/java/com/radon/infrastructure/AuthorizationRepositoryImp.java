package com.radon.infrastructure;

import com.radon.application.port.out.AuthorizationRepository;
import com.radon.domain.Authority;
import com.radon.domain.Role;
import com.radon.infrastructure.entity.AuthorityEntity;
import com.radon.infrastructure.entity.RoleEntity;
import com.radon.infrastructure.jpa.AuthorityJpaRepository;
import com.radon.infrastructure.jpa.RoleJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class AuthorizationRepositoryImp implements AuthorizationRepository {

    private final AuthorityJpaRepository authorityJpaRepository;
    private final RoleJpaRepository roleJpaRepository;


    public AuthorizationRepositoryImp(AuthorityJpaRepository authorityJpaRepository, RoleJpaRepository roleJpaRepository) {
        this.authorityJpaRepository = authorityJpaRepository;
        this.roleJpaRepository = roleJpaRepository;
    }

    @Override
    public Authority createAuthority(Authority authority) {
        return Authority.of(authorityJpaRepository.save(new AuthorityEntity(authority.authority())));
    }

    @Override
    public Role createRole(Role role) {

        Set<AuthorityEntity> authorityEntities = authorityJpaRepository.findAllByAuthorityIn(role.authorities().stream().map(Authority::authority).collect(Collectors.toSet()));

        return Role.of(roleJpaRepository.save(new RoleEntity(role.role(),authorityEntities)));
    }
}
