package com.example.task.dto;

import javax.persistence.Column;

public class PositionDTO {

    private String position;

    private String workerId;


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }
}
