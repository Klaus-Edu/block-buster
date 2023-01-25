package com.example.blockbuster.blockbuster.services;

import com.example.blockbuster.blockbuster.models.ClientModel;
import com.example.blockbuster.blockbuster.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public List<ClientModel> findAll() {
        return clientRepository.findAll();
    }

    @Transactional
    public ClientModel save(ClientModel clientModel){
        return clientRepository.save(clientModel);
    }

    @Transactional
    public void delete(ClientModel clientModel) {
        clientRepository.delete(clientModel);
    }
}
