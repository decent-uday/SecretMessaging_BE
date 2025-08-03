package com.secret.io.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secret.io.advice.AuthNotFoundException;
import com.secret.io.dto.GenericResponse;
import com.secret.io.dto.SecretUserDto;
import com.secret.io.entity.SecretUser;
import com.secret.io.repository.SecretUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    SecretUserRepository secretUserRepository;
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public GenericResponse saveUser(SecretUserDto secretUser) {
        log.info("UserServiceImpl :: saveUser : " + secretUser.getUserId());
        GenericResponse genericResponse = new GenericResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        SecretUser user = objectMapper.convertValue(secretUser, SecretUser.class);
        if (secretUserRepository.findByUserIdAndUserSecret(user.getUserId(),
                secretUser.getUserSecret()) != null) {
            genericResponse.setMessage("User is already registered!! Can login directly!");
            genericResponse.setStatus(HttpStatus.CONFLICT.toString());
            return genericResponse;
        }
        secretUserRepository.save(user);
        genericResponse.setMessage("User saved successfully!!! Now you can login and enjoy secret messages!!!");
        genericResponse.setStatus(HttpStatus.CREATED.toString());
        return genericResponse;
    }

    @Override
    public GenericResponse retrieveUser(Map headers) {
        log.info("UserServiceImpl :: getUser : " + headers.toString());
        GenericResponse genericResponse = new GenericResponse();
        String auth = (String) headers.get("Authorization");
        if(auth == null) {
            throw new AuthNotFoundException("Missing authorization in the request!!!");
        }
        String encodedAuth = auth.split("(?i)basic ")[1];
        byte[] decodedBytes = Base64.getDecoder().decode(encodedAuth);
        String decoded = new String(decodedBytes, StandardCharsets.UTF_8);
        String[] decodedArray = decoded.split(":", 2);
        String userId = decodedArray.length > 0 ? decodedArray[0] : "";
        String userSecret = decodedArray.length > 1 ? decodedArray[1] : "";
        if (secretUserRepository.findByUserIdAndUserSecret(userId,
                userSecret) != null) {
            genericResponse.setMessage("User is Authenticated!");
            genericResponse.setStatus(HttpStatus.OK.toString());
            return genericResponse;
        }
        return null;
    }
}
