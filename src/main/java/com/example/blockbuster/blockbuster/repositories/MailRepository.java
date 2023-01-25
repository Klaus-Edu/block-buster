package com.example.blockbuster.blockbuster.repositories;

import com.example.blockbuster.blockbuster.models.MailModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<MailModel, Long> {
}
