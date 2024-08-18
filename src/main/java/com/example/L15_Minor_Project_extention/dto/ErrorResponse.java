package com.example.L15_Minor_Project_extention.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private Integer httpStatus;
    private String exception;
    private String message;
}
