package com.radon.domain;

import com.radon.exception.types.IllegalArgumentException;
import com.radon.infrastructure.entity.UserEntity;
import com.radon.presentation.dto.UserRegisterRequest;
import lombok.Builder;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
public record User(
        UUID id,
        String firstName,
        String lastName,
        String userName,
        String password,
        Set<Role> roles
) implements UserDetails {

    public User {
        if(firstName == null || firstName.length() < 3){
            throw new IllegalArgumentException("First name is required and must be at least 3 characters long!");
        }
        if(lastName == null || lastName.length() < 3){
            throw new IllegalArgumentException("Last name is required and must be at least 3 characters long!");
        }
        if(userName == null || userName.length() < 5){
            throw new IllegalArgumentException("Username is required and must be at least 5 characters long!");
        }
        if(password == null || password.length() < 6){
            throw new IllegalArgumentException("Password is required and must be at least 6 characters long!");
        }
        if(roles == null || roles.isEmpty()){
            throw new IllegalArgumentException("Roles are required and must be at least 1 role!");
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.roles.stream().map(Role::authorities).flatMap(Collection::stream).collect(Collectors.toList());
        var listOfAuthorities = this.roles.stream()
                .map(role -> new SimpleGrantedAuthority(
                        "ROLE_" + role.role()
                ))
                .collect(Collectors.toSet());
        listOfAuthorities.addAll(
                this.roles.stream().map(Role::authorities).flatMap(Collection::stream).map(item -> new SimpleGrantedAuthority(item.authority())).toList()
        );
        return listOfAuthorities;
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static User of(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .userName(userEntity.getUsername())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .password(userEntity.getPassword())
                .roles(userEntity.getRoles().stream().map(Role::of).collect(Collectors.toSet()))
                .build();
    }

    public static User of(UserRegisterRequest request) {
        return User.builder()
                .userName(request.userName())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .password(request.password())
                .roles(request.roles().stream().map(item -> Role.builder()
                        .role(item.name())
                        .build()
                ).collect(Collectors.toSet()))
                .build();
    }
}
