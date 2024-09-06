package com.userservice.userservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {

    private String firstName;
    private  String lastName;
    private String email;
    private String roleName;
    private String street;
    private String city;
    private String state;
    private String zip;

}
