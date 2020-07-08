package ru.bocharova.se.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "app_project")
public final class Project extends AbstractEntity implements Serializable {

    private String id = UUID.randomUUID().toString();

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

    public Project(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
