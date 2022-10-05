package com.example.task.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkerDTO {

    private String id;

    private String name;

    private String surname;

    private String department;

    private String position;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
