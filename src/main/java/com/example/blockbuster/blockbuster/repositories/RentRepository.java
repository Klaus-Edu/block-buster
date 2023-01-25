package com.example.blockbuster.blockbuster.repositories;

import com.example.blockbuster.blockbuster.models.RentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RentRepository extends JpaRepository<RentModel, Long> {
}
