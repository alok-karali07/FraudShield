package com.example.service.api.user;

import com.example.transaction.rest.api.UserApi;
import com.example.transaction.rest.model.RestUserRequest;
import com.example.transaction.rest.model.RestUserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserApi {

    @Override
    public ResponseEntity<RestUserResponse> createUser(RestUserRequest restUserRequest) {
        return null;
    }

    @Override
    public ResponseEntity<RestUserResponse> getUserById(Long id) {
        return null;
    }
}