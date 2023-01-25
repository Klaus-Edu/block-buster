package com.example.blockbuster.blockbuster.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;


@Getter
@Setter
public class ClientDto {

    @NotBlank
    private String name;
    @NotBlank
    @CPF
    private String cpf;
    @NotBlank
    private String tel;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String address;
    @NotNull
    private int age;

}
