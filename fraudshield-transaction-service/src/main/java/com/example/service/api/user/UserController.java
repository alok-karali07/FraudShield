package com.example.service.api.user;

import com.example.service.api.user.internal.UserService;
import com.example.service.factory.UserEntityFactory;
import com.example.service.model.UserEntity;
import com.example.transaction.rest.api.UserApi;
import com.example.transaction.rest.model.RestUserRequest;
import com.example.transaction.rest.model.RestUserResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static com.example.service.factory.RestUserResponseFactory.buildRestUserResponse;

@RestController
@AllArgsConstructor(onConstructor_ = @Autowired)
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<RestUserResponse> createUser(RestUserRequest restUserRequest) {

        UserEntity userEntity =
                userService.createUser(UserEntityFactory.buildUserEntity(restUserRequest));

        RestUserResponse response = buildRestUserResponse(userEntity);

        return ResponseEntity
                .created(URI.create("/api/v1/users/" + userEntity.getId()))
                .body(response);
    }

    @Override
    public ResponseEntity<RestUserResponse> getUserById(Long id) {
        return null;
    }
}