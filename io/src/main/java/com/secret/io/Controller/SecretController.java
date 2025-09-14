package com.secret.io.Controller;


import com.secret.io.Service.SecretService;
import com.secret.io.dto.GenericResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RequestMapping("/v1/services/")
@RestController
public class SecretController {

    @Autowired
    SecretService secretService;

    @PostMapping("/encrypt")
    ResponseEntity<GenericResponse> encryptMessage(@Valid @RequestBody Map message) {
        GenericResponse genericResponse =  secretService.encryptMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(genericResponse);
    }

    @PostMapping("/decrypt")
    ResponseEntity<GenericResponse> decryptMessage(@Valid @RequestBody Map message) {
        GenericResponse genericResponse =  secretService.decryptMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(genericResponse);
    }
}
