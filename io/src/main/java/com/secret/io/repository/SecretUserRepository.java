package com.secret.io.repository;

import com.secret.io.entity.SecretUser;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SecretUserRepository extends MongoRepository<SecretUser, ObjectId> {

    SecretUser findByUserIdAndUserSecret(String userId, String userSecret);

    SecretUser findByUserId(String userId);
}
