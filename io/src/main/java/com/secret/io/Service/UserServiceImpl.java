package com.secret.io.Service;

import com.secret.io.entity.SecretUser;
import com.secret.io.repository.SecretUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SecretUserRepository secretUserRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public SecretUser registerUser(String userId, String rawPassword) {
        if (secretUserRepository.findByUserId(userId) != null) {
            throw new RuntimeException("User already exists");
        }

        SecretUser user = new SecretUser();
        user.setUserId(userId);
        user.setUserSecret(passwordEncoder.encode(rawPassword)); // hash password
        return secretUserRepository.save(user);
    }

    @Override
    public boolean authenticateUser(String userId, String rawPassword) {
        SecretUser user = secretUserRepository.findByUserId(userId);
        return user != null && passwordEncoder.matches(rawPassword, user.getUserSecret());
    }
}

