package com.secret.io.Service;

import com.secret.io.dto.GenericResponse;

import java.util.Map;

public interface SecretService {

    GenericResponse encryptMessage(Map message);

    GenericResponse decryptMessage(Map<String, String> message);

}
