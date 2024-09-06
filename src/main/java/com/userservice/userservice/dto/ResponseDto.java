package com.userservice.userservice.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Data
public class ResponseDto{

    private String status;

    private HashMap<String,String> errorMessage;

    private Map<String, Object> responseData;

    public ResponseDto() {

        errorMessage = new HashMap<>();
        responseData = new HashMap<>();
    }

}
