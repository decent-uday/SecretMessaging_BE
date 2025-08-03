package com.secret.io.Service;

import com.secret.io.dto.GenericResponse;
import com.secret.io.dto.SecretUserDto;
import com.secret.io.entity.SecretUser;

import java.util.Map;


public interface UserService {
    GenericResponse saveUser(SecretUserDto secretUser);

    GenericResponse retrieveUser(Map headers);
}
