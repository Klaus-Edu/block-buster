package com.example.blockbuster.blockbuster.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentDto {

    @NotNull
    private Long clientID;
    @NotNull
    private String title;
    @NotNull
    private String movieGender;
    @NotNull
    private Double price;



}
