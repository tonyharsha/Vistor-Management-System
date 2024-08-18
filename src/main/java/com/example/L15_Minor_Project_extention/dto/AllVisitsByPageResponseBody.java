package com.example.L15_Minor_Project_extention.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllVisitsByPageResponseBody {
    private List<VisitDto> visitDtos;

    private Long totalRow;

    private Integer totalPage;


}
