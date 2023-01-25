package com.example.blockbuster.blockbuster.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "TB_CLIENT")
public class ClientModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 200)
    private String name;
    @Column(nullable = false, unique = true)
    @CPF
    private String cpf;
    @Column(nullable = false)
    private int age;
    @Column(nullable = false, unique = true, length = 11)
    private String tel;
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    @Column(nullable = false, unique = true, length = 200)
    private String address;
    @OneToMany(mappedBy = "clientModel")
    @JsonManagedReference
    private List<RentModel> rentModel;


    public ClientModel() {

    }

    public ClientModel(String name, String cpf, String tel, String email, String address, int age) {
        this.name = name;
        this.cpf = cpf;
        this.tel = tel;
        this.email = email;
        this.address = address;
        this.age = age;
    }

}
