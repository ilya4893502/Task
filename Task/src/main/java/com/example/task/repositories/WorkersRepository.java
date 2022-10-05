package com.example.task.repositories;

import com.example.task.models.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkersRepository extends JpaRepository<Worker, String> {
    Optional<Worker> findById(String id);
}
