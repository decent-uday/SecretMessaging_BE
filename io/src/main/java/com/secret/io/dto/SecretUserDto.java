package com.secret.io.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;
import jakarta.validation.constraints.NotNull;

@Setter
@Getter
@Data
@Component
public class SecretUserDto {

    @JsonSerialize(using = ObjectIdSerializer.class)
    @Id
    ObjectId id;

    @NotNull(message = "userId should not be null")
    String userId;
    @NotNull(message = "password should not be null")
    String userSecret;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserSecret() {
        return userSecret;
    }

    public void setUserSecret(String userSecret) {
        this.userSecret = userSecret;
    }
}
