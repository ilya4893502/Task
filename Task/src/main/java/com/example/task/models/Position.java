package com.example.task.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Position")
public class Position implements Serializable {

    @Id
    @Column(name = "position")
    private String position;

    @Column(name = "worker_id")
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position1 = (Position) o;
        return Objects.equals(position, position1.position) && Objects.equals(workerId, position1.workerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, workerId);
    }
}
