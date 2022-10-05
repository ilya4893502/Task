package com.example.task.repositories;

import com.example.task.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentsRepository extends JpaRepository<Department, String> {
    Optional<Department> findBySurname(String surname);
    Optional<Department> findByDepartment(String department);
}
