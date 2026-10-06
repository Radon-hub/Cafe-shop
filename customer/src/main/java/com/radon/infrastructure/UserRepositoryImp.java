package com.radon.infrastructure;

import com.radon.application.port.out.UserRepository;
import com.radon.domain.User;
import com.radon.exception.types.RoleNotFoundException;
import com.radon.exception.types.UserExistsException;
import com.radon.exception.types.UserNotFoundException;
import com.radon.infrastructure.entity.RoleEntity;
import com.radon.infrastructure.entity.UserEntity;
import com.radon.infrastructure.jpa.RoleJpaRepository;
import com.radon.infrastructure.jpa.UserJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserRepositoryImp implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final RoleJpaRepository roleJpaRepository;

    public UserRepositoryImp(UserJpaRepository userJpaRepository, RoleJpaRepository roleJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.roleJpaRepository = roleJpaRepository;
    }

    @Override
    public User registerUser(User user) {

        Optional<UserEntity> existedUser = userJpaRepository.findByUsername(user.getUsername());

        if (existedUser.isPresent()) {
            throw new UserExistsException(user.getUsername());
        }

        Set<RoleEntity> roles = new HashSet<>();

        user.roles().forEach(role -> {
           roles.add(roleJpaRepository.findByRole(role.role()).orElseThrow(() -> new RoleNotFoundException(role.role())));
        });

        return User.of(
                userJpaRepository.save(
                        new UserEntity(
                                user.firstName(),
                                user.lastName(),
                                user.userName(),
                                user.password(),
                                roles
                        )
                )
        );
    }

    @Override
    public User getUserByUsername(String username) {
        return User.of(userJpaRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username)));
    }
}
