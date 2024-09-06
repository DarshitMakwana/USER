package com.userservice.userservice.dto;

import lombok.Data;

@Data
public class   UserRequestDto {

    private String firstName;
    private  String lastName;
    private String email;
    private String roleName;
    private String street;
    private String city;
    private String state;
    private String zip;
}
