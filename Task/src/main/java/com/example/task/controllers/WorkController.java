package com.example.task.controllers;

import com.example.task.dto.DepartmentDTO;
import com.example.task.dto.PositionDTO;
import com.example.task.dto.WorkerDTO;
import com.example.task.models.Department;
import com.example.task.models.Position;
import com.example.task.models.Worker;
import com.example.task.services.DepartmentsService;
import com.example.task.services.PositionsService;
import com.example.task.services.WorkersService;
import com.example.task.util.WorkerError;
import com.example.task.util.WorkerNotCreatedException;
import com.example.task.util.WorkerNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class WorkController {

    private final WorkersService workersService;
    private final PositionsService positionsService;
    private final DepartmentsService departmentsService;
    private final ModelMapper modelMapper;

    @Autowired
    public WorkController(WorkersService workersService, PositionsService positionsService, DepartmentsService departmentsService, ModelMapper modelMapper) {
        this.workersService = workersService;
        this.positionsService = positionsService;
        this.departmentsService = departmentsService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/workers")
    public List<WorkerDTO> allWorkers() {
        return workersService.allWorkers().stream()
                .map(this::convertToWorkerDTO1).collect(Collectors.toList());
    }


    @GetMapping("/positions")
    public List<PositionDTO> allPositions() {
        return positionsService.allPositions().stream()
                .map(this::convertToPositionDTO).collect(Collectors.toList());
    }


    @GetMapping("/departments")
    public List<DepartmentDTO> allDepartments() {
        return departmentsService.allDepartments().stream()
                .map(this::departmentDTO).collect(Collectors.toList());
    }


    @GetMapping("/workers/{id}")
    public WorkerDTO worker(@PathVariable("id") String id) {
        return convertToWorkerDTO2(workersService.worker(id));
    }


    @GetMapping("/workers_wit_department")
    public List<WorkerDTO> allWorkersWithDepartment() {
        return workersService.allWorkers().stream()
                .map(this::convertToWorkerDTO3).collect(Collectors.toList());
    }


    @PostMapping("/add_worker")
    public ResponseEntity<HttpStatus> addWorker(@RequestBody WorkerDTO workerDTO) {
        Optional<Department> existingDepartment = departmentsService.department(workerDTO.getSurname());
        if (existingDepartment.isPresent() && existingDepartment.get().getPosition()
                .equals(workerDTO.getPosition())
                && existingDepartment.get().getSurname().equals(workerDTO.getSurname())) {
            throw new WorkerNotCreatedException();
        }
        workersService.addWorker(convertToWorker(workerDTO), workerDTO.getPosition(),
                workerDTO.getDepartment());
        return ResponseEntity.ok(HttpStatus.OK);
    }



    @ExceptionHandler
    private ResponseEntity<WorkerError> notFoundWorker(WorkerNotFoundException e) {
        WorkerError workerError = new WorkerError("Такого работника не существует!");
        return new ResponseEntity<>(workerError, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    private ResponseEntity<WorkerError> notCreatedWorker(WorkerNotCreatedException e) {
        WorkerError workerError = new WorkerError("Связка должность-фамилия должна быть уникальна!");
        return new ResponseEntity<>(workerError, HttpStatus.BAD_REQUEST);
    }



    private WorkerDTO convertToWorkerDTO1(Worker worker) {
        return modelMapper.map(worker, WorkerDTO.class);
    }


    private WorkerDTO convertToWorkerDTO2(Worker worker) {

        WorkerDTO workerDTO = new WorkerDTO();

        workerDTO.setName(worker.getName());
        workerDTO.setSurname(worker.getSurname());

        Optional<Department> department = departmentsService.department(worker.getSurname());
        Optional<Position> position = positionsService.position(worker.getId());
        if (department.isPresent()) {
            workerDTO.setDepartment(department.get().getDepartment());
        } else {
            workerDTO.setDepartment("нет отдела");

        }
        if (position.isPresent()) {
            workerDTO.setPosition(position.get().getPosition());
        } else {
            workerDTO.setPosition("нет должности");
        }

        return workerDTO;
    }


    private WorkerDTO convertToWorkerDTO3(Worker worker) {
        WorkerDTO workerDTO = convertToWorkerDTO2(worker);
        workerDTO.setId(worker.getId());
        return workerDTO;
    }


    private Worker convertToWorker(WorkerDTO workerDTO) {
        return modelMapper.map(workerDTO, Worker.class);
    }


    private PositionDTO convertToPositionDTO(Position position) {
        return modelMapper.map(position, PositionDTO.class);
    }


    private DepartmentDTO departmentDTO(Department department) {
        return modelMapper.map(department, DepartmentDTO.class);
    }
}
