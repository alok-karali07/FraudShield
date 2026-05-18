package com.example.service.api.user;

import com.example.service.api.user.service.UserService;
import com.example.service.exception.NotFoundException;
import com.example.service.factory.UserEntityFactory;
import com.example.service.factory.rest.RestUserResponseFactory;
import com.example.transaction.rest.api.UserApi;
import com.example.transaction.rest.model.RestUserRequest;
import com.example.transaction.rest.model.RestUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<RestUserResponse> createUser(RestUserRequest restUserRequest) {

        return ResponseEntity.ok(
                RestUserResponseFactory.createUserResponse(
                        userService.createUser(UserEntityFactory.buildUserEntity(restUserRequest))
                )
        );
    }

    @Override
    public ResponseEntity<RestUserResponse> getUserById(Long id) {

        return userService.getUserById(id)
                .map(userEntity -> ResponseEntity.ok(RestUserResponseFactory.createUserResponse(userEntity)))
                .orElseThrow(() -> new NotFoundException("User not found!"));
    }
}