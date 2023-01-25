package com.example.blockbuster.blockbuster.controllers;

import com.example.blockbuster.blockbuster.dtos.ClientDto;
import com.example.blockbuster.blockbuster.models.ClientModel;
import com.example.blockbuster.blockbuster.repositories.ClientRepository;
import com.example.blockbuster.blockbuster.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/clients")
public class ClientController {

    final ClientService clientService;
    private final ClientRepository clientRepository;

    public ClientController(ClientService clientService,
                            ClientRepository clientRepository){
        this.clientService = clientService;
        this.clientRepository = clientRepository;
    }

    @PostMapping
    public ResponseEntity<Object> saveClient(@RequestBody @Valid ClientDto clientDto){

        ClientModel clientModel = new ClientModel();

        BeanUtils.copyProperties(clientDto, clientModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.save(clientModel));
    }

    @GetMapping
    public ResponseEntity<List<ClientModel>> getAllClients(){
        return ResponseEntity.status(HttpStatus.OK).body(clientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getClientById(@PathVariable(value = "id") Long id) {
        Optional<ClientModel> clientModelOp = clientRepository.findById(id);

        if(clientModelOp.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(clientModelOp.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID not found!");
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Object> editClientById(@PathVariable(value = "id") Long id,@RequestBody ClientDto clientDto){
        Optional<ClientModel> clientModelOp = clientRepository.findById(id);

        if(clientModelOp.isPresent()){
            ClientModel clientModel = clientModelOp.get();

            clientModel.setName(clientDto.getName());
            clientModel.setCpf(clientDto.getCpf());
            clientModel.setTel(clientDto.getTel());
            clientModel.setEmail(clientDto.getEmail());
            clientModel.setAddress(clientDto.getAddress());
            clientModel.setAge(clientDto.getAge());

            return ResponseEntity.status(HttpStatus.OK).body(clientService.save(clientModel));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no client registered with this ID!");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteClientById(@PathVariable(value = "id") Long id){
        Optional<ClientModel> clientModelOp = clientRepository.findById(id);

        if(clientModelOp.isPresent()){
            clientService.delete(clientModelOp.get());
            return ResponseEntity.status(HttpStatus.OK).body("Client "+ id +" successfully deleted!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no client registered with this ID!");
        }
    }



}
