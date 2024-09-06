package com.userservice.userservice.controller;

import com.userservice.userservice.dto.ResponseDto;
import com.userservice.userservice.dto.UserRequestDto;
import com.userservice.userservice.dto.UserResponseDto;
import com.userservice.userservice.model.User;
import com.userservice.userservice.service.UserService;
import com.userservice.userservice.utils.ResponseStatusEnum;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody UserRequestDto userRequestDto) {

        final ResponseDto response = new ResponseDto();
        ResponseEntity<ResponseDto> responseEntity = null;
        final Map<String, Object> map = new HashMap<>();
        final HashMap<String, String> errors = new HashMap<>();

        try{
            if (userRequestDto != null) {
                Optional<User> byEmail = userService.findByEmail(userRequestDto.getEmail());
                //checkig duplication of email
                if (byEmail.isPresent()) {
                    errors.put("Email", "Email is already registered");
                    response.setStatus(ResponseStatusEnum.FAILED.status());
                    response.setErrorMessage(errors);
                    responseEntity = new ResponseEntity<>(response, null, HttpStatus.INTERNAL_SERVER_ERROR);
                } else {
                    //if every validations seems perfect save user
                    UserResponseDto user = userService.saveUser(userRequestDto);
                    map.put("USER", user);
                    response.setStatus(ResponseStatusEnum.SUCCESS.status());
                    response.setResponseData(map);
                    responseEntity = new ResponseEntity<>(response, null, HttpStatus.OK);
                }
            } else {
                //if the DTO is null throw error
                errors.put("UserDto", "UserDto cannot be null");
                response.setStatus(ResponseStatusEnum.FAILED.status());
                response.setErrorMessage(errors);
                responseEntity = new ResponseEntity<>(response, null, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            errors.put("User", e.getMessage());
            response.setErrorMessage(errors);
            response.setStatus(ResponseStatusEnum.FAILED.status());
            responseEntity = new ResponseEntity<>(response, null, HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
        final ResponseDto response = new ResponseDto();
        ResponseEntity<ResponseDto> responseEntity = null;
        final Map<String, Object> map = new HashMap<>();
        final HashMap<String, String> errors = new HashMap<>();

        try {
            UserResponseDto userResponse = userService.getUserById(Long.valueOf(id));
            map.put("User", userResponse);
            response.setStatus(ResponseStatusEnum.SUCCESS.status());
            response.setResponseData(map);
            responseEntity = new ResponseEntity<>(response, null, HttpStatus.OK);
        } catch (Exception e) {
            errors.put("User", "User with " + id + " is not present");
            response.setErrorMessage(errors);
            response.setStatus(ResponseStatusEnum.FAILED.status());
            responseEntity = new ResponseEntity<>(response, null, HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> deactiveUser(@PathVariable("email") String email) {
        final ResponseDto response = new ResponseDto();
        ResponseEntity<ResponseDto> responseEntity = null;
        final Map<String, Object> map = new HashMap<>();
        final HashMap<String, String> errors = new HashMap<>();

        try {
            String deactiveUser = userService.deleteUserByEmail(email);
            map.put("User", deactiveUser);
            response.setStatus(ResponseStatusEnum.SUCCESS.status());
            response.setResponseData(map);
            responseEntity = new ResponseEntity<>(response, null, HttpStatus.OK);
        } catch (Exception e) {
            errors.put("User", e.getMessage());
            response.setErrorMessage(errors);
            response.setStatus(ResponseStatusEnum.FAILED.status());
            responseEntity = new ResponseEntity<>(response, null, HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }



    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UserRequestDto userRequestDto) {
        final ResponseDto response = new ResponseDto();
        ResponseEntity<ResponseDto> responseEntity = null;
        final Map<String, Object> map = new HashMap<>();
        final HashMap<String, String> errors = new HashMap<>();

        try {
            UserResponseDto updatedUser = userService.updateUser(userRequestDto);
            map.put("User", updatedUser);
            response.setStatus(ResponseStatusEnum.SUCCESS.status());
            response.setResponseData(map);
            responseEntity = new ResponseEntity<>(response, null, HttpStatus.OK);
        } catch (Exception e) {
            errors.put("User", e.getMessage());
            response.setErrorMessage(errors);
            response.setStatus(ResponseStatusEnum.FAILED.status());
            responseEntity = new ResponseEntity<>(response, null, HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }






}
