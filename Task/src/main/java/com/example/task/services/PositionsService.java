package com.example.task.services;

import com.example.task.models.Position;
import com.example.task.models.Worker;
import com.example.task.repositories.PositionsRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PositionsService {

    private final PositionsRepository positionsRepository;
    private final EntityManager entityManager;

    @Autowired
    public PositionsService(PositionsRepository positionsRepository, EntityManager entityManager) {
        this.positionsRepository = positionsRepository;
        this.entityManager = entityManager;
    }


    public List<Position> allPositions() {
        return positionsRepository.findAll();
    }


    public Optional<Position> position(String workerId) {
        return positionsRepository.findByWorkerId(workerId);
    }


    @Transactional
    public void addPosition(Worker worker, String position) {
        Session session = entityManager.unwrap(Session.class);
        Position positionObject = new Position();
        positionObject.setPosition(position);
        positionObject.setWorkerId(worker.getId());
        session.save(positionObject);
    }
}
