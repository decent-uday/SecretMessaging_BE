package com.secret.io.Controller;

import com.secret.io.Service.UserService;
import com.secret.io.dto.GenericResponse;
import com.secret.io.dto.SecretUserDto;
import com.secret.io.entity.SecretUser;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequestMapping(path = "/auth")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    ResponseEntity<GenericResponse> saveUser(@Valid @RequestBody SecretUserDto secretUser) {
        GenericResponse genericResponse = userService.saveUser(secretUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(genericResponse);
    }

    @GetMapping("/login")
    ResponseEntity<GenericResponse> retrieveUser(@RequestHeader Map headers) {
        GenericResponse genericResponse = userService.retrieveUser(headers);
        return ResponseEntity.status(HttpStatus.OK).body(genericResponse);
    }


}
