package com.example.L15_Minor_Project_extention.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitorDto {


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
    private String idNumber;


    private AddressDto address;
}
