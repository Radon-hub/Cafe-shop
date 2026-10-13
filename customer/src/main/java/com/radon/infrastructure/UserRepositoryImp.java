package com.radon.infrastructure;

import com.radon.application.port.out.UserRepository;
import com.radon.application.service.JwtUtil;
import com.radon.domain.JwtTokens;
import com.radon.domain.TokenType;
import com.radon.domain.User;
import com.radon.exception.types.*;
import com.radon.infrastructure.entity.RoleEntity;
import com.radon.infrastructure.entity.UserEntity;
import com.radon.infrastructure.jpa.RoleJpaRepository;
import com.radon.infrastructure.jpa.UserJpaRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserRepositoryImp implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UserRepositoryImp(UserJpaRepository userJpaRepository, RoleJpaRepository roleJpaRepository, AuthenticationManager authenticationManager, JwtUtil jwtUtil,PasswordEncoder passwordEncoder) {
        this.userJpaRepository = userJpaRepository;
        this.roleJpaRepository = roleJpaRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
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
                                passwordEncoder.encode(user.password()),
                                roles
                        )
                )
        );
    }

    @Override
    public JwtTokens loginUser(String username, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        return new JwtTokens(accessToken, refreshToken);
    }

    @Override
    public JwtTokens refreshToken(String refreshToken) {

        String username = jwtUtil.extractUsername(refreshToken);

        TokenType tokenType = jwtUtil.getTokenType(refreshToken);

        if(tokenType != TokenType.REFRESH){
            throw new InvalidTokenTypeException("This token did not provided for refreshing!");
        }

        UserDetails userDetails = User.of(userJpaRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username)));

        if(!jwtUtil.isTokenValid(refreshToken,userDetails)) {
            throw new InvalidTokenException();
        }

        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String newRefreshToken = jwtUtil.generateRefreshToken(userDetails);

        return new JwtTokens(accessToken, newRefreshToken);

    }

}
