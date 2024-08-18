package com.example.L15_Minor_Project_extention.dto;

import com.example.L15_Minor_Project_extention.enums.UserStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull
    @Size(max=255)
    private String name;

    @NotNull
    @Size(max=255)
    private String email;

    @NotNull
    @Size(max=255)
    private String phoneNo;

    @NotNull
    @Size(max=255)
    private String role;

    @NotNull
    private UserStatus status;

    @NotNull
    private String idNumber;

    private String flatNo;

    @NotNull
    private String password;

    private AddressDto addressDto;



}
