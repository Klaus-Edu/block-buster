package com.example.blockbuster.blockbuster.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "TB_RENT")
public class RentModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 70)
    private String title;
    @Column(nullable = false)
    private String movieGender;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime requestDay;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private  ClientModel clientModel;

    public RentModel(){

    }

    public RentModel(ClientModel clientModel) {
        this.clientModel = clientModel;
    }


}
