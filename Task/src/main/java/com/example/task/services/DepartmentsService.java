package com.example.task.services;

import com.example.task.models.Department;
import com.example.task.models.Worker;
import com.example.task.repositories.DepartmentsRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DepartmentsService {

    private final DepartmentsRepository departmentsRepository;
    private final EntityManager entityManager;

    @Autowired
    public DepartmentsService(DepartmentsRepository departmentsRepository, EntityManager entityManager) {
        this.departmentsRepository = departmentsRepository;
        this.entityManager = entityManager;
    }


    public List<Department> allDepartments() {
        return departmentsRepository.findAll();
    }


    public Optional<Department> department(String surname) {
        return departmentsRepository.findBySurname(surname);
    }


    @Transactional
    public void addDepartment(Worker worker, String department, String position) {
        Session session = entityManager.unwrap(Session.class);
        Department departmetObject = new Department();
        departmetObject.setDepartment(department);
        departmetObject.setPosition(position);
        departmetObject.setSurname(worker.getSurname());
        session.save(departmetObject);
    }
}
