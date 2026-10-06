package com.radon.application.port.out;

import com.radon.domain.Authority;
import com.radon.domain.Role;

public interface AuthorizationRepository {
    Authority createAuthority(Authority authority);
    Role createRole(Role role);
    Boolean checkRolesExists(Role role);
}
