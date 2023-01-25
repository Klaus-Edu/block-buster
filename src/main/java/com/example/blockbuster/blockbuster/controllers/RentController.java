package com.example.blockbuster.blockbuster.controllers;

import com.example.blockbuster.blockbuster.dtos.RentDto;
import com.example.blockbuster.blockbuster.enums.StatusEmail;
import com.example.blockbuster.blockbuster.models.ClientModel;
import com.example.blockbuster.blockbuster.models.MailModel;
import com.example.blockbuster.blockbuster.models.RentModel;
import com.example.blockbuster.blockbuster.repositories.ClientRepository;
import com.example.blockbuster.blockbuster.repositories.MailRepository;
import com.example.blockbuster.blockbuster.repositories.RentRepository;
import com.example.blockbuster.blockbuster.services.RentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/rent")

public class RentController {

    final RentService rentService;
    private final RentRepository rentRepository;
    private final ClientRepository clientRepository;

    final ObjectMapper objectMapper;
    @Autowired
    private JavaMailSender emailSender;
    private final MailRepository mailRepository;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy' as 'HH:mm:ss");

    public RentController(RentService rentService,
                          RentRepository rentRepository,
                          ClientRepository clientRepository,
                          ObjectMapper objectMapper, MailRepository mailRepository){
        this.rentService = rentService;
        this.rentRepository = rentRepository;
        this.clientRepository = clientRepository;
        this.objectMapper = objectMapper;
        this.mailRepository = mailRepository;
    }

    @PostMapping
    public ResponseEntity<Object> saveRent(@RequestBody @Valid RentDto rentDto){
        RentModel rentModel = new RentModel();
        MailModel mailModel = new MailModel();
        BeanUtils.copyProperties(rentDto, rentModel);

        Optional<ClientModel> clientModelOp = clientRepository.findById(rentDto.getClientID());

        if(!clientModelOp.isEmpty()){
            rentModel.setRequestDay(LocalDateTime.now());
            rentModel.setClientModel(clientModelOp.get());
            try{
                mailModel.setSendDateEmail(rentModel.getRequestDay());
                mailModel.setEmailFrom("padilhaklaus@gmail.com");
                mailModel.setEmailTo(clientModelOp.get().getEmail());
                mailModel.setSubject("Locação: " + rentModel.getTitle());
                mailModel.setText("Olá " + clientModelOp.get().getName() +"! Estamos entrando em contato para informar que a locação do filme: '" + rentModel.getTitle() + "', foi efetuada com sucesso na data: " + rentModel.getRequestDay().format(formatter));

                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(mailModel.getEmailFrom());
                message.setTo(mailModel.getEmailTo());
                message.setSubject(mailModel.getSubject());
                message.setText(mailModel.getText());
                emailSender.send(message);

                mailModel.setStatusEmail(StatusEmail.SENT);
            } catch (MailException e){
                mailModel.setStatusEmail(StatusEmail.ERROR);
            } finally {
                mailRepository.save(mailModel);
                return ResponseEntity.status(HttpStatus.CREATED).body(rentService.save(rentModel));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client id Not found!");
        }

    }

    @GetMapping
    public ResponseEntity<List<RentModel>> getAllRent(){
        return ResponseEntity.status(HttpStatus.OK).body(rentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRentById(@PathVariable(value = "id") Long id){
        Optional<RentModel> rentModelOp = rentRepository.findById(id);

        if(rentModelOp.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(rentModelOp.get());
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID not found!");
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Object> editRentById(@PathVariable(value = "id") Long id, @RequestBody RentDto rentDto){
        Optional<RentModel> rentModelOp = rentRepository.findById(id);

        if(rentModelOp.isPresent()){
            RentModel rentModel = rentModelOp.get();
            Optional<ClientModel> clientModelOp = clientRepository.findById(rentDto.getClientID());

            rentModel.setTitle(rentDto.getTitle());
            rentModel.setMovieGender(rentDto.getMovieGender());
            rentModel.setPrice(rentDto.getPrice());
            rentModel.setClientModel(clientModelOp.get());
            return ResponseEntity.status(HttpStatus.OK).body(rentService.save(rentModel));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id not found!");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteRentById(@PathVariable(value = "id") Long id){
        Optional<RentModel> rentModelOp = rentRepository.findById(id);

        if(rentModelOp.isPresent()){
            rentService.delete(rentModelOp.get());
            return ResponseEntity.status(HttpStatus.OK).body("Rent Request "+ id +" successfully deleted!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no Rent Request registered with this ID!");
        }
    }
}
