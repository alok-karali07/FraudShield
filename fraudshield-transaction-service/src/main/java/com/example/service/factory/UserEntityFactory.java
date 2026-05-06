package com.example.service.factory;

import com.example.service.model.UserEntity;
import com.example.transaction.rest.model.RestUserRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntityFactory {

    public static UserEntity buildUserEntity(RestUserRequest restUserRequest) {

        return Optional.ofNullable(restUserRequest)
                .map(userRequest -> UserEntity.builder()
                        .firstname(userRequest.getFirstName())
                        .lastname(userRequest.getLastName())
                        .email(userRequest.getEmail())
                        .phone(userRequest.getPhone())
                        .build())
                .orElse(null);
    }
}