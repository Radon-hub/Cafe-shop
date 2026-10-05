package com.radon.infrastructure;

import com.radon.application.port.out.UserRepository;
import com.radon.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImp implements UserRepository {
    @Override
    public User registerUser(User user) {
        return null;
    }
}
