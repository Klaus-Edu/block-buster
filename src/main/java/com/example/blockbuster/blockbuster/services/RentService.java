package com.example.blockbuster.blockbuster.services;

import com.example.blockbuster.blockbuster.models.RentModel;
import com.example.blockbuster.blockbuster.repositories.RentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentService {

    final RentRepository rentRepository;

    public RentService(RentRepository rentRepository){
        this.rentRepository = rentRepository;
    }

    @Transactional
    public RentModel save(RentModel rentModel) {
        return rentRepository.save(rentModel);
    }

    public List<RentModel> findAll() {
        return rentRepository.findAll();
    }

    @Transactional
    public void delete(RentModel rentModel) {
        rentRepository.delete(rentModel);
    }
}
