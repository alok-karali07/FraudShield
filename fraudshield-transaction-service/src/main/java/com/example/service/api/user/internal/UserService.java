package com.example.service.api.user.internal;

import com.example.service.model.UserEntity;

public interface UserService {

    UserEntity createUser(UserEntity transactionEntity);
}