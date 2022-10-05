package com.example.task.services;

import com.example.task.models.Department;
import com.example.task.models.Position;
import com.example.task.models.Worker;
import com.example.task.repositories.WorkersRepository;
import com.example.task.util.WorkerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class WorkersService {

    private final WorkersRepository workersRepository;
    private final DepartmentsService departmentsService;
    private final PositionsService positionsService;

    @Autowired
    public WorkersService(WorkersRepository workersRepository, DepartmentsService departmentsService, PositionsService positionsService) {
        this.workersRepository = workersRepository;
        this.departmentsService = departmentsService;
        this.positionsService = positionsService;
    }


    public List<Worker> allWorkers() {
        return workersRepository.findAll();
    }


    public Worker worker(String id) {
        return workersRepository.findById(id).orElseThrow(WorkerNotFoundException::new);
    }


    @Transactional
    public void addWorker(Worker worker, String position, String department) {

        if (position != null) {
            positionsService.addPosition(worker, position);
        }

        if (department != null) {
            departmentsService.addDepartment(worker, department, position);
        }

        workersRepository.save(worker);
    }
}
