package com.example.service.factory.rest;

import com.example.service.model.UserEntity;
import com.example.transaction.rest.model.RestUserResponse;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class RestUserResponseFactory {

    public RestUserResponse createUserResponse(UserEntity userEntity) {

        return Optional.ofNullable(userEntity)
                .map(user -> new RestUserResponse()
                        .id(user.getId())
                        .firstName(user.getFirstname())
                        .lastName(user.getLastname())
                        .email(user.getEmail())
                        .phone(user.getPhone()))
                .orElse(null);
    }
}
