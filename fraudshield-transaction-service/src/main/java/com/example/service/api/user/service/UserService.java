package com.example.service.api.user.service;

import com.example.service.model.UserEntity;

import java.util.Optional;

public interface UserService {

    UserEntity createUser(UserEntity userEntity);

    Optional<UserEntity> getUserById(Long id);
}
