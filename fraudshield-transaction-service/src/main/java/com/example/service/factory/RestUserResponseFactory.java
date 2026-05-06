package com.example.service.factory;

import com.example.service.model.UserEntity;
import com.example.transaction.rest.model.RestUserResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RestUserResponseFactory {

    public static RestUserResponse buildRestUserResponse(UserEntity userEntity) {

        return Optional.ofNullable(userEntity)
                .map(user -> new RestUserResponse()
                        .id(user.getId())
                        .firstName(user.getFirstname())
                        .lastName(user.getLastname())
                        .email(user.getEmail())
                        .phone(user.getPhone())
                        .createdAt(OffsetDateTime.now()))
                .orElse(null);

    }
}