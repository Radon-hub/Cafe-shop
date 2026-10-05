package com.radon.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;


@Entity
@Table(name = "authorities")
@Getter
@Setter
public class AuthorityEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String authority;

    @Override
    public @Nullable String getAuthority() {
        return authority;
    }

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at",nullable = false)
    private Instant updatedAt;

    public AuthorityEntity(String authority) {
        this.authority = authority;
    }

    public AuthorityEntity() {}

}
