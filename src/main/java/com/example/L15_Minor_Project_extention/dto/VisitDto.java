package com.example.L15_Minor_Project_extention.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisitDto {



    @NotNull
    @Size(max = 255)
    private String purpose;

    private Date inTime;

    private Date outTime;

    @NotNull
    @Size(max = 255)
    private String urlofImage;

    @NotNull
    private Integer noOfPeople;

    @NotNull
    private String flatNumber;

    @NotNull
    private String idNumber;


    private String visitorName;
}
