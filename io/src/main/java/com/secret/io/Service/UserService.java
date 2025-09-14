package com.secret.io.Service;

import com.secret.io.entity.SecretUser;

public interface UserService {
    SecretUser registerUser(String userId, String rawPassword);
    boolean authenticateUser(String userId, String rawPassword);
}

