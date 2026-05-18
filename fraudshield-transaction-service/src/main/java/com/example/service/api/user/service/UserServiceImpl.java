package com.example.service.api.user.service;

import com.example.service.model.UserEntity;
import com.example.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity createUser(UserEntity userEntity) {

        return userRepository.save(userEntity);
    }

    @Override
    public Optional<UserEntity> getUserById(Long id) {

        return userRepository.findById(id);
    }
}
