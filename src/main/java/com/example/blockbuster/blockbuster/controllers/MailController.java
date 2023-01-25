package com.example.blockbuster.blockbuster.controllers;

import com.example.blockbuster.blockbuster.models.MailModel;
import com.example.blockbuster.blockbuster.models.RentModel;
import com.example.blockbuster.blockbuster.services.MailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/emails")
public class MailController {

    MailService mailService;
    @GetMapping
    public ResponseEntity<List<MailModel>> getAllRent(){
        return ResponseEntity.status(HttpStatus.OK).body(mailService.findAll());
    }
}
