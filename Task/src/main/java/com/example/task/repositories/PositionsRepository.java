package com.example.task.repositories;

import com.example.task.models.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PositionsRepository extends JpaRepository<Position, String> {
    Optional<Position> findByWorkerId(String workerId);
}
