package ru.bocharova.se.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class Task implements Serializable{

//    private static final long serialVersionUID = 1L;

    private String id = UUID.randomUUID().toString();

    private String projectId;

    private String name = "";

    private Date dateBegin;

    private Date dateEnd;

    public void test() {
        System.out.println("HELLO");
    }

//    public Long test = 11L;

//    public String orderIndex = Long.toString(System.currentTimeMillis());
//    public Long orderIndex = System.currentTimeMillis();

//    private String test() {
//        return "";
//    }


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

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

}
