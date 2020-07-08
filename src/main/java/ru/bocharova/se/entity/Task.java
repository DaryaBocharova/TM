package ru.bocharova.se.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "app_task")
public final class Task extends AbstractEntity implements Serializable{

    private String id = UUID.randomUUID().toString();

    private String projectId;

    private String name = "";

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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Task(String id, String projectId, String name) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
    }
}
