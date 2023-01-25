package com.example.blockbuster.blockbuster.repositories;

import com.example.blockbuster.blockbuster.models.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<ClientModel, Long> {


}
