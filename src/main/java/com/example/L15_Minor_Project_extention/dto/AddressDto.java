package com.example.L15_Minor_Project_extention.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    @NotNull
    @Size(max=255)
    private String line1;

    @NotNull
    @Size(max=255)
    private String line2;

    @NotNull
    @Size(max=255)
    private String city;

    @NotNull
    @Size(max=255)
    private String pincode;

    @NotNull
    @Size(max=255)
    private String country;
}
