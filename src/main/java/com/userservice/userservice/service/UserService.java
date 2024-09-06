package com.userservice.userservice.service;

import com.userservice.userservice.dto.UserRequestDto;
import com.userservice.userservice.dto.UserResponseDto;
import com.userservice.userservice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserResponseDto saveUser(UserRequestDto userRequestDto);
    UserResponseDto getUserById(Long id);
    String deleteUserByEmail(String email);
    Optional<User> findByEmail(String email);
    UserResponseDto updateUser(UserRequestDto userRequestDto);
}
